package com.akdong.we.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountResponse {
    private String bankCode;
    private String bankName;
    private String userName;
    private String accountNo;
    private String accountName;
    private String accountTypeCode;
    private String accountTypeName;
    private String accountCreatedDate;
    private String accountExpiryDate;
    private String dailyTransferLimit;
    private String oneTimeTransferLimit;
    private String accountBalance;
    private String lastTransactionDate;
    private String currency;
}