package com.data.api

import com.data.model.request.RequestCouple
import com.data.model.response.ResponseCouples
import com.data.model.response.ResponseCouplesCode
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CoupleApi {
    @GET("couples/code")
    suspend fun getCoupleCode(): ResponseCouplesCode

    @POST("couples")
    suspend fun postCouple(@Body requestCouple: RequestCouple): ResponseCouples
}