package com.data.datasourceimpl

import com.data.api.BankApi
import com.data.datasource.BankDataSource
import com.data.model.request.RequestAccountAuth
import com.data.model.request.RequestAuthCode
import com.data.model.request.RequestTransfer
import com.data.model.response.ResponseAccountAuth
import com.data.model.response.ResponseAuthCode
import com.data.model.response.ResponseBank
import com.data.model.response.ResponseCoupleAccount
import com.data.model.response.ResponseTransfer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BankDataSourceImpl @Inject constructor(
    private val bankApi: BankApi

) : BankDataSource {
    override suspend fun getMyAccountTest(): ResponseBank {
        return bankApi.getMyAccountTest()
    }

    override suspend fun getMyAccount(): ResponseBank {
        return bankApi.getMyAccount()
    }

    override suspend fun checkAuthCode(requestAuthCode: RequestAuthCode): ResponseAuthCode {
        return bankApi.checkAuthCode(requestAuthCode)
    }

    override suspend fun accountAuth(requestAccountAuth: RequestAccountAuth): ResponseAccountAuth {
        return bankApi.accountAuth(requestAccountAuth)
    }

    override suspend fun getMyCoupleAccount(): ResponseCoupleAccount {
        return bankApi.getMyCoupleAccount()
    }

    override suspend fun postTransfer(requestTransfer: RequestTransfer): ResponseTransfer {
        return bankApi.postTransfer(requestTransfer)
    }
}