package com.data.datasource

import com.data.model.request.RequestCouple
import com.data.model.response.ResponseCouples
import com.data.model.response.ResponseCouplesCode
import com.data.model.response.ResponseGetCouples

interface CoupleDataSource {
    suspend fun getCoupleCode(): ResponseCouplesCode

    suspend fun postCouple(requestCouple: RequestCouple) : ResponseCouples

    suspend fun getCouples(): ResponseGetCouples
}