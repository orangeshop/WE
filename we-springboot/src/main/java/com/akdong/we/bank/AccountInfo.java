package com.akdong.we.bank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountInfo {
    @Schema(description = "계좌 번호", example = "0016800061891065")
    private final String accountNo;
}
