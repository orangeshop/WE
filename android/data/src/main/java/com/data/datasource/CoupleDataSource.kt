package com.data.datasource

import com.data.model.request.RequestCouple
import com.data.model.response.ResponseCouples
import com.data.model.response.ResponseCouplesCode

interface CoupleDataSource {
    suspend fun getCoupleCode(): ResponseCouplesCode

    suspend fun postCouple(requestCouple: RequestCouple) : ResponseCouples
}