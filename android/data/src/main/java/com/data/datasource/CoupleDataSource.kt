package com.data.datasource

import com.data.model.response.ResponseCouple

interface CoupleDataSource {
    suspend fun getCoupleCode(): ResponseCouple
}