package com.akdong.we.ledger;

import com.akdong.we.common.dto.SuccessResponse;
import com.akdong.we.common.exception.BusinessException;
import com.akdong.we.couple.entity.Couple;
import com.akdong.we.couple.service.CoupleService;
import com.akdong.we.file.service.FileService;
import com.akdong.we.ledger.entity.Ledger;
import com.akdong.we.member.Login;
import com.akdong.we.member.entity.Member;
import com.akdong.we.member.exception.member.MemberErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/ledgers")
@Slf4j
@RequiredArgsConstructor
@Tag(name="Ledger API", description = "장부 API입니다.")
public class LedgerController {
    private final LedgerService ledgerService;
    private final CoupleService coupleService;


    @PostMapping
    @Operation(summary = "장부 생성", description = "장부를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "장부 생성 성공", useReturnTypeSchema = true)
    })
    public ResponseEntity<?> join(@Parameter(hidden = true)  @Login Member member) {
        Couple couple = coupleService.getMyCoupleInfo(member)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.COUPLE_NOT_FOUND_ERROR));
        Ledger ledger = ledgerService.createLedger(couple);

        // 데이터 객체 생성
        Map<String, Long> response = new HashMap<>();
        response.put("LedgerId", ledger.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>("성공적으로 장부를 생성했습니다.", response)
        );

    }
}
