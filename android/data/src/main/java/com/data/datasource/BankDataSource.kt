package com.data.datasource

import com.data.model.request.RequestAccountAuth
import com.data.model.request.RequestAuthCode
import com.data.model.request.RequestTransfer
import com.data.model.response.ResponseAccountAuth
import com.data.model.response.ResponseAuthCode
import com.data.model.response.ResponseBank
import com.data.model.response.ResponseCoupleAccount
import com.data.model.response.ResponseTransfer

interface BankDataSource {
    suspend fun getMyAccount(): ResponseBank

    suspend fun checkAuthCode(requestAuthCode: RequestAuthCode): ResponseAuthCode

    suspend fun accountAuth(requestAccountAuth: RequestAccountAuth): ResponseAccountAuth

    suspend fun getMyCoupleAccount(): ResponseCoupleAccount

    suspend fun postTransfer(requestTransfer: RequestTransfer): ResponseTransfer
}