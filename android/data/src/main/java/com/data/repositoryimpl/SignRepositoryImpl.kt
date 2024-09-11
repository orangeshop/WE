package com.data.repositoryimpl

import com.data.datasource.SignDataSource
import com.data.model.request.RequestSignUp
import com.data.model.response.ResponseSignUp
import com.data.repository.SignRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource
) : SignRepository {
    override fun postSignUp(requestSignUp: RequestSignUp): Flow<ApiResult<ResponseSignUp>> {
        return flow {
            safeApiCall {
                signDataSource.postSignUp(requestSignUp)
            }
        }
    }
}