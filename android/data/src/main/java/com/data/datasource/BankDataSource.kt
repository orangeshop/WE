package com.data.datasource

import com.data.model.request.RequestAuthCode
import com.data.model.response.ResponseAuthCode
import com.data.model.response.ResponseBank

interface BankDataSource {
    suspend fun getMyAccount(): ResponseBank

    suspend fun checkAuthCode(requestAuthCode: RequestAuthCode): ResponseAuthCode
}