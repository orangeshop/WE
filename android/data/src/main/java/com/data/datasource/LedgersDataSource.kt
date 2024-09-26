package com.data.datasource

import com.data.model.response.ResponseGetLedgers
import com.data.model.response.ResponsePostLedgers

interface LedgersDataSource {
    suspend fun postLedgers(): ResponsePostLedgers

    suspend fun getLedgers(): ResponseGetLedgers
}