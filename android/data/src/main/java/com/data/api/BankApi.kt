package com.data.api

import com.data.model.response.ResponseBank
import retrofit2.http.GET

interface BankApi {
    @GET("bank/my-account")
    suspend fun getMyAccount(): ResponseBank

}