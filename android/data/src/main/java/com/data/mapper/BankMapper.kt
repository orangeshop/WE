package com.data.mapper

import com.data.model.response.ResponseAccountAuth
import com.data.model.response.ResponseAuthCode
import com.data.model.response.ResponseBank
import com.data.model.response.ResponseCoupleAccount
import com.data.model.response.ResponseTransfer
import com.we.model.AccountAuthData
import com.we.model.AuthCodeData
import com.we.model.BankData
import com.we.model.CoupleAccountData
import com.we.model.TransferData

fun ResponseBank.Data.toEntity(): BankData {
    return BankData(
        accountBalance = accountBalance,
        accountCreatedDate = accountCreatedDate,
        accountExpiryDate =accountExpiryDate,
        accountName = accountName,
        accountNo = accountNo,
        accountTypeCode = accountTypeCode,
        accountTypeName = accountTypeName,
        bankCode = bankCode,
        bankName = bankName,
        currency = currency,
        dailyTransferLimit = dailyTransferLimit,
        lastTransactionDate = lastTransactionDate,
        oneTimeTransferLimit = oneTimeTransferLimit,
        userName = userName
    )
}

fun ResponseAuthCode.Data.toModel(): AuthCodeData{
    return AuthCodeData(
        Status = Status
    )
}

fun ResponseAccountAuth.Data.toModel(): AccountAuthData{
    return AccountAuthData(
        authCode = authCode
    )
}

fun ResponseCoupleAccount.Data.toModel(): CoupleAccountData {
    return CoupleAccountData(
        accountBalance,
        accountCreatedDate,
        accountExpiryDate,
        accountName,
        accountNo,
        accountTypeCode,
        accountTypeName,
        bankCode,
        bankName,
        currency,
        dailyTransferLimit,
        lastTransactionDate,
        oneTimeTransferLimit,
        userName
    )
}

fun ResponseTransfer.Data.toModel() : TransferData {
    return TransferData(
        STATUS
    )
}
