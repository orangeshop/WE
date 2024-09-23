package com.data.repositoryimpl

import com.data.datasource.BankDataSource
import com.data.mapper.toEntity
import com.data.mapper.toModel
import com.data.model.request.RequestAuthCode
import com.data.model.request.RequestCouple
import com.data.model.response.ResponseBank
import com.data.repository.BankRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.AuthCodeData
import com.we.model.BankData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(
    private val bankDataSource: BankDataSource

) : BankRepository {
    override suspend fun getMyAccount(): Flow<ApiResult<List<BankData>>> {
        return flow {

            val result = safeApiCall {
                val bankList = bankDataSource.getMyAccount().data.map { it.toEntity() }
                bankList  // 반환된 리스트를 직접 반환
            }
            emit(result)
        }
    }

    override suspend fun checkAuthCode(requestAuthCode: RequestAuthCode): Flow<ApiResult<AuthCodeData>> {
        return flow {
            val result = safeApiCall {
                val authCode = bankDataSource.checkAuthCode(requestAuthCode).data.toModel()
                authCode
            }
            emit(result)
        }
    }
}