package com.data.repository

import com.data.model.request.RequestAccountAuth
import com.data.model.request.RequestAuthCode
import com.data.model.request.RequestCouple
import com.data.model.request.RequestTransfer
import com.data.model.response.ResponseBank
import com.data.util.ApiResult
import com.we.model.AccountAuthData
import com.we.model.AuthCodeData
import com.we.model.BankData
import com.we.model.CoupleAccountData
import com.we.model.TransferData
import kotlinx.coroutines.flow.Flow

interface BankRepository {
    suspend fun getMyAccountTest() : Flow<ApiResult<List<BankData>>>

    suspend fun getMyAccount() : Flow<ApiResult<List<BankData>>>

    suspend fun checkAuthCode(requestAuthCode: RequestAuthCode) : Flow<ApiResult<AuthCodeData>>

    suspend fun accountAuth(requestAccountAuth: RequestAccountAuth) : Flow<ApiResult<AccountAuthData>>

    suspend fun getMyCoupleAccount() : Flow<ApiResult<CoupleAccountData>>

    suspend fun postTransfer(requestTransfer: RequestTransfer) : Flow<ApiResult<TransferData>>
}