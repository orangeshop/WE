package com.akdong.we.bank;

import com.akdong.we.api.FinApiCallService;
import com.akdong.we.common.dto.ErrorResponse;
import com.akdong.we.common.dto.SuccessResponse;
import com.akdong.we.member.Login;
import com.akdong.we.member.entity.Member;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/bank")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Bank API", description = "Bank API입니다.")
public class BankController {
    private final FinApiCallService finApiCallService;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
}
