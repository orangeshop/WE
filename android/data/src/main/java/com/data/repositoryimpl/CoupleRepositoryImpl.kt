package com.data.repositoryimpl

import com.data.datasource.CoupleDataSource
import com.data.model.response.ResponseCouple
import com.data.repository.CoupleRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.CoupleParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoupleRepositoryImpl @Inject constructor(
    private val coupleDataSource: CoupleDataSource
): CoupleRepository {
    override fun getCoupleCode(): Flow<ApiResult<ResponseCouple>> {
        return flow {
            safeApiCall {
                coupleDataSource.getCoupleCode()
            }
        }
    }
}