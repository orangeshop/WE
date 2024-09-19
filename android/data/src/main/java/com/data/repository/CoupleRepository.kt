package com.data.repository

import com.data.model.response.ResponseCouple
import com.data.util.ApiResult
import com.we.model.CoupleData
import kotlinx.coroutines.flow.Flow

interface CoupleRepository {
    fun getCoupleCode() : Flow<ApiResult<CoupleData>>
}