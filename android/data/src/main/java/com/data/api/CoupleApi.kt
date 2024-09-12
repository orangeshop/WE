package com.data.api

import com.data.model.response.ResponseCouple
import retrofit2.http.GET

interface CoupleApi {
    @GET("couple/code")
    suspend fun getCoupleCode(): ResponseCouple
}