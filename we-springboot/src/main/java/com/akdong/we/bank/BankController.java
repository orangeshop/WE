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
import com.akdong.we.member.entity.MemberAccount;
import com.akdong.we.member.exception.member.MemberErrorCode;
import com.akdong.we.member.repository.MemberAccountRepository;
import com.akdong.we.notification.service.FirebaseService;
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

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("v1/bank")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Bank API", description = "Bank API입니다.")
public class BankController {
    private final FinApiCallService finApiCallService;
    private final CoupleService coupleService;
    private final FirebaseService firebaseService;
    private final BankService bankService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final GiftRepository giftRepository;
    private final LedgerRepository ledgerRepository;
    private final LedgerGiftRepository ledgerGiftRepository;
    private final MemberAccountRepository memberAccountRepository;

    @GetMapping("/my-account-test")
    @Operation(summary = "나의 계좌 목록(1원 송금 등록용)", description = "나의 계좌 목록을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계좌 목록 조회 성공", useReturnTypeSchema = true),
    })
    public ResponseEntity<?> myAccountTest(@Parameter(hidden = true)  @Login Member member){
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

    @GetMapping("/my-account")
    @Operation(summary = "나의 계좌 목록(등록 완료된 계좌)", description = "나의 계좌 목록(등록 완료된 계좌)을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계좌 목록 조회 성공", useReturnTypeSchema = true),
    })
    public ResponseEntity<?> myAccount(@Parameter(hidden = true)  @Login Member member){
        try {
            List<GetAccountResponse> myAccounts = bankService.accountList(member);
            return ResponseEntity.ok(
                    new SuccessResponse<>(
                            "나의 계좌(등록 완료된 계좌) 리스트 조회에 성공했습니다.",
                            myAccounts
                    )
            );
        }catch (Exception e) {
            log.error("Exception occurred while processing the account list response: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("나의 계좌(등록 완료된 계좌) 리스트 조회 중 오류가 발생했습니다.", e));
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
            @RequestBody PostAccountAuthRequest request) throws IOException {
        String authCode = finApiCallService.openAccountAuth(member.getUserKey(), request.getAccountNo());

        try{
            firebaseService.sendMessageTo(member.getId(), "인증 코드입니다.", authCode);
        }catch (Exception e){
            log.info(e.getMessage());
        }

        // "authCode": authCode 형식으로 Map을 생성
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("authCode", authCode);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        "1원 송금 요청에 성공했습니다.",
                        responseMap
                )
        );
    }

    @PostMapping("/checkAuthCode")
    @Operation(summary = "1원 송금 검증", description = "1원 송금 검증을 요청합니다.")
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
            MemberAccount memberAccount = MemberAccount.builder()
                    .member(member)
                    .accountNo(request.getAccountNo())
                    .build();
            memberAccountRepository.save(memberAccount);
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
            @RequestBody TransferRequest request) throws IOException {

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
        Ledger ledger = ledgerRepository.findById(request.getLedgerId())
                        .orElseThrow(() -> new BusinessException(LedgerErrorCode.LEDGER_NOT_FOUND_ERROR));
        Couple couple = ledger.getCouple();
        Member member1 = couple.getMember1();
        Member member2 = couple.getMember2();

        // 아직 알림 연동(디바이스 토큰 설정) 재대로 안되서 오류남
        //firebaseService.sendMessageTo(member1.getId(), "입금 알림", String.valueOf(request.getTransactionBalance())+"원 입금되었습니다.");
        //firebaseService.sendMessageTo(member2.getId(), "입금 알림", String.valueOf(request.getTransactionBalance())+"원 입금되었습니다.");

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        "송금에 성공했습니다.",
                        responseMap
                )
        );
    }

    @GetMapping("/my-couple-account")
    @Operation(summary = "나의 커플 계좌 정보", description = "나의 커플 계좌 정보를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "커플 계좌 조회 성공", useReturnTypeSchema = true),
    })
    public ResponseEntity<?> myCoupleAccount(@Parameter(hidden = true)  @Login Member member){

        if(member.isCoupleJoined()) {
            Couple couple = coupleService.getMyCoupleInfo(member)
                    .orElseThrow(() -> new BusinessException(MemberErrorCode.COUPLE_NOT_FOUND_ERROR));
            String accountNo = couple.getAccountNumber();

            if(accountNo != null){
                try {
                    JsonNode jsonResponse = finApiCallService.getCoupleAccount(member.getUserKey(), accountNo);
                    GetAccountResponse response = objectMapper.treeToValue(jsonResponse, GetAccountResponse.class);

                    return ResponseEntity.ok(
                            new SuccessResponse<>(
                                    "나의 커플 계좌 조회에 성공했습니다.",
                                    response
                            )
                    );
                }catch (Exception e) {
                    log.error("Exception occurred while processing the account list response: {}", e.getMessage(), e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ErrorResponse<>("계좌 리스트 조회 중 오류가 발생했습니다.", e));
                }
            }else{
                return ResponseEntity.ok(
                        new SuccessResponse<>(
                                "나의 커플 계좌 조회에 성공했습니다.",
                                null
                        )
                );
            }
        }else{
            return ResponseEntity.ok(
                    new SuccessResponse<>(
                            "나의 커플 계좌 조회에 성공했습니다.",
                            null
                    )
            );
        }
    }
}
