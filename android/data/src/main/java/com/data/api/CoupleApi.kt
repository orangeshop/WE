package com.data.api

import com.data.model.request.RequestCouple
import com.data.model.response.ResponseCouple
import retrofit2.http.GET

interface CoupleApi {
    @GET("couples/code")
    suspend fun getCoupleCode(): ResponseCouple
}