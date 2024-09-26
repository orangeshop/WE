package com.data.repositoryimpl

import com.data.datasource.BankDataSource
import com.data.mapper.toEntity
import com.data.mapper.toModel
import com.data.model.request.RequestAccountAuth
import com.data.model.request.RequestAuthCode
import com.data.model.request.RequestCouple
import com.data.model.request.RequestTransfer
import com.data.model.response.ResponseBank
import com.data.repository.BankRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.AccountAuthData
import com.we.model.AuthCodeData
import com.we.model.BankData
import com.we.model.CoupleAccountData
import com.we.model.TransferData
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
                bankList
            }
            emit(result)
        }
    }

    override suspend fun getMyAccountTest(): Flow<ApiResult<List<BankData>>> {
        return flow {
            val result = safeApiCall {
                val bankList = bankDataSource.getMyAccountTest().data.map { it.toEntity() }
                bankList
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

    override suspend fun accountAuth(requestAccountAuth: RequestAccountAuth): Flow<ApiResult<AccountAuthData>> {
        return flow{
            val result = safeApiCall {
                val authCode = bankDataSource.accountAuth(requestAccountAuth).data.toModel()
                authCode
            }
            emit(result)
        }
    }

    override suspend fun getMyCoupleAccount(): Flow<ApiResult<CoupleAccountData>> {
        return flow{
            val result = safeApiCall {
                val authCode = bankDataSource.getMyCoupleAccount().data.toModel()
                authCode
            }
            emit(result)
        }
    }

    override suspend fun postTransfer(requestTransfer: RequestTransfer): Flow<ApiResult<TransferData>> {
        return flow{
            val result = safeApiCall {
                val authCode = bankDataSource.postTransfer(requestTransfer).data.toModel()
                authCode
            }
            emit(result)
        }
    }
}