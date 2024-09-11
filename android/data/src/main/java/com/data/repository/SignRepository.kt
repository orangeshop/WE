package com.data.repository

import com.data.model.request.RequestSignUp
import com.data.model.response.ResponseSignUp
import com.data.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface SignRepository {

    fun postSignUp(requestSignUp: RequestSignUp) : Flow<ApiResult<ResponseSignUp>>

}