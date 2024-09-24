package com.data.datasource

import com.data.model.request.RequestAccountAuth
import com.data.model.request.RequestAuthCode
import com.data.model.response.ResponseAccountAuth
import com.data.model.response.ResponseAuthCode
import com.data.model.response.ResponseBank

interface BankDataSource {
    suspend fun getMyAccount(): ResponseBank

    suspend fun checkAuthCode(requestAuthCode: RequestAuthCode): ResponseAuthCode

    suspend fun accountAuth(requestAccountAuth: RequestAccountAuth): ResponseAccountAuth
}