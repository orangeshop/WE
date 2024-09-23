package com.akdong.we.couple.response;

import com.akdong.we.couple.entity.Couple;
import com.akdong.we.member.response.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoupleInfo {
    @Schema(description = "아이디", example = "1")
    private final long id;

    @Schema(description = "멤버 1")
    private final MemberInfo member1;

    @Schema(description = "멤버 2")
    private final MemberInfo member2;

    @Schema(description = "계좌 등록 여부", example = "true")
    private boolean bankbookCreated;

    @Column(name="계좌 번호")
    private String accountNumber;

    @Column(name ="장부 생성 여부")
    private boolean ledgerCreated;

    public static CoupleInfo of(Couple couple) {
        return builder()
                .id(couple.getId())
                .member1(MemberInfo.of(couple.getMember1()))
                .member2(MemberInfo.of(couple.getMember2()))
                .bankbookCreated(couple.isBankbookCreated())
                .accountNumber(couple.getAccountNumber())
                .ledgerCreated(couple.isLedgerCreated())
                .build();
    }
}
