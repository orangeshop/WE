package com.data.repository

import com.data.model.response.ResponseBank
import com.data.util.ApiResult
import com.we.model.BankData
import kotlinx.coroutines.flow.Flow

interface BankRepository {
    suspend fun getMyAccount() : Flow<ApiResult<List<BankData>>>
}