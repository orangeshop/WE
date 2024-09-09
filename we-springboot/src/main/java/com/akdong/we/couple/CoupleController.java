package com.akdong.we.couple;

import com.akdong.we.common.dto.SuccessResponse;
import com.akdong.we.couple.entity.Couple;
import com.akdong.we.couple.request.CoupleRegisterRequest;
import com.akdong.we.couple.service.CoupleService;
import com.akdong.we.member.Login;
import com.akdong.we.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/couples")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "couple API", description = "Couple API입니다.")
public class CoupleController {
    private final CoupleService coupleService;

    @GetMapping("/code")
    @Operation(summary = "코드 생성", description = "커플 초대 코드를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "커플 코드 생성 성공", useReturnTypeSchema = true)
    })
    public ResponseEntity<?> code(@Parameter(hidden = true)  @Login Member member){
        String code = coupleService.createCode(member.getId());

        // 데이터 객체 생성
        Map<String, String> response = new HashMap<>();
        response.put("code", code);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>("성공적으로 코드를 생성했습니다.", response)
        );
    }

    @PostMapping
    @Operation(summary = "커플 생성", description = "커플을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "커플 생성 성공", useReturnTypeSchema = true)
    })
    public ResponseEntity<?> join(@RequestBody CoupleRegisterRequest coupleRegisterRequest, @Parameter(hidden = true)  @Login Member member) {
        Couple couple = coupleService.createCouple(coupleRegisterRequest, member.getId());

        // 데이터 객체 생성
        Map<String, Long> response = new HashMap<>();
        response.put("coupleId", couple.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>("성공적으로 커플을 생성했습니다.", response)
        );

    }

}
