package com.data.api

import com.data.model.request.RequestAccountAuth
import com.data.model.request.RequestAuthCode
import com.data.model.response.ResponseAccountAuth
import com.data.model.response.ResponseAuthCode
import com.data.model.response.ResponseBank
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BankApi {
    @GET("bank/my-account")
    suspend fun getMyAccount(): ResponseBank

    @POST("bank/checkAuthCode")
    suspend fun checkAuthCode(@Body request: RequestAuthCode): ResponseAuthCode

    @POST("bank/accountAuth")
    suspend fun accountAuth(@Body request: RequestAccountAuth): ResponseAccountAuth
}