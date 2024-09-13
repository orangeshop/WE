package com.data.repositoryimpl

import com.data.datasource.SignDataSource
import com.data.mapper.toModel
import com.data.model.response.ResponseSignUp
import com.data.repository.SignRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.LoginParam
import com.we.model.SignUpParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource
) : SignRepository {
    override fun postSignUp(signUpParam: SignUpParam): Flow<ApiResult<ResponseSignUp>> {
        return flow {
            safeApiCall {
                signDataSource.postSignUp(signUpParam.toModel())
            }
        }
    }

    override fun postLogin(loginParam: LoginParam): Flow<ApiResult<ResponseSignUp>> {
        return flow {
            safeApiCall {
                signDataSource.postLogin(loginParam.toModel())
            }
        }
    }
}