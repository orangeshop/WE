package com.akdong.we.bank;

import com.akdong.we.api.FinApiCallService;
import com.akdong.we.common.dto.ErrorResponse;
import com.akdong.we.common.dto.SuccessResponse;
import com.akdong.we.common.exception.BusinessException;
import com.akdong.we.couple.entity.Couple;
import com.akdong.we.couple.service.CoupleService;
import com.akdong.we.ledger.LedgerErrorCode;
import com.akdong.we.ledger.entity.Gift;
import com.akdong.we.ledger.entity.Ledger;
import com.akdong.we.ledger.entity.LedgerGift;
import com.akdong.we.ledger.repository.GiftRepository;
import com.akdong.we.ledger.repository.LedgerGiftRepository;
import com.akdong.we.ledger.repository.LedgerRepository;
import com.akdong.we.member.Login;
import com.akdong.we.member.entity.Member;
import com.akdong.we.member.exception.member.MemberErrorCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("v1/bank")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Bank API", description = "Bank API입니다.")
public class BankController {
    private final FinApiCallService finApiCallService;
    private final CoupleService coupleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final GiftRepository giftRepository;
    private final LedgerRepository ledgerRepository;
    private final LedgerGiftRepository ledgerGiftRepository;

    @GetMapping("/my-account")
    @Operation(summary = "나의 계좌 목록(1원 송금 등록용)", description = "나의 계좌 목록을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계좌 목록 조회 성공", useReturnTypeSchema = true),
    })
    public ResponseEntity<?> myAccount(@Parameter(hidden = true)  @Login Member member){
        try {
            JsonNode jsonResponse = finApiCallService.accountList(member.getUserKey());
            List<GetAccountResponse> response = new ArrayList<>();
            for (JsonNode node : jsonResponse) {
                response.add(objectMapper.treeToValue(node, GetAccountResponse.class));
            }

            return ResponseEntity.ok(
                    new SuccessResponse<>(
                            "나의 계좌 리스트 조회에 성공했습니다.",
                            response
                    )
            );
        }catch (Exception e) {
            log.error("Exception occurred while processing the account list response: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("계좌 리스트 조회 중 오류가 발생했습니다.", e));
        }
    }
    @PostMapping("/accountAuth")
    @Operation(summary = "1원 송금 요청", description = "1원 송금을 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "1원 송금 요청 성공", useReturnTypeSchema = true),
    })
    @Transactional
    public ResponseEntity<?> accountAuth(
            @Parameter(hidden = true)  @Login Member member,
            @RequestBody PostAccountAuthRequest request){
        String authCode = finApiCallService.openAccountAuth(member.getUserKey(), request.getAccountNo());

        // "authCode": authCode 형식으로 Map을 생성
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("authCode", authCode);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        "나의 계좌 리스트 조회에 성공했습니다.",
                        responseMap
                )
        );
    }

    @PostMapping("/checkAuthCode")
    @Operation(summary = "1원 송금 검증 요청", description = "1원 송금 검증을 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "1원 송금 검증 요청 성공", useReturnTypeSchema = true),
    })
    @Transactional
    public ResponseEntity<?> checkAuthCode(
            @Parameter(hidden = true) @Login Member member,
            @RequestBody CheckAuthUserRequest request){

        String status = finApiCallService.checkAuthCode(member.getUserKey(), request.getAccountNo(), request.getAuthText(), request.getAuthCode());

        // "authCode": authCode 형식으로 Map을 생성
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("Status", status);

        if(status.equals("SUCCESS")){
            Couple couple = coupleService.getMyCoupleInfo(member)
                    .orElseThrow(() ->new BusinessException(MemberErrorCode.COUPLE_NOT_FOUND_ERROR));
            couple.setBankbookCreated(true);
            couple.setAccountNumber(request.getAccountNo());
        }


        return ResponseEntity.ok(
                new SuccessResponse<>(
                        "1원 송금 검증 요청에 성공했습니다.",
                        responseMap
                )
        );
    }

    @PostMapping("/transfer")
    @Operation(summary = "계좌 이체", description = "금융망으로 계좌 이체를 요청합니다..")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계좌 이체 성공", useReturnTypeSchema = true),
    })
    @Transactional
    public ResponseEntity<?> transfer(
            @Parameter(hidden = true)  @Login Member member,
            @RequestBody TransferRequest request){

        String responseCode = finApiCallService.transfer(member.getUserKey(), request);

        if(Objects.equals(responseCode, "H0000")){
            Gift gift = Gift.builder()
                    .member(member)
                    .isBride(request.getIsBride())
                    .charge(request.getTransactionBalance())
                    .build();
            giftRepository.save(gift);


            // 장부도 추가해야함. 지금 장부 생성 api가 없어서 여기까지만
            Ledger ledger = ledgerRepository.findById(request.getLedgerId())
                    .orElseThrow(() -> new BusinessException(LedgerErrorCode.LEDGER_NOT_FOUND_ERROR));

            LedgerGift ledgerGift = LedgerGift.builder()
                    .ledger(ledger)
                    .gift(gift)
                    .build();

            ledgerGiftRepository.save(ledgerGift);
        }

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("STATUS", "Success");

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        "송금에 성공했습니다.",
                        responseMap
                )
        );
    }
}
