package com.data.repository

import com.data.model.request.RequestSignUp
import com.data.model.response.ResponseSignUp
import com.data.util.ApiResult
import com.we.model.LoginParam
import com.we.model.SignUpParam
import kotlinx.coroutines.flow.Flow

interface SignRepository {

    fun postSignUp(signUpParam: SignUpParam) : Flow<ApiResult<ResponseSignUp>>

    fun postLogin(loginParam: LoginParam) : Flow<ApiResult<ResponseSignUp>>
}