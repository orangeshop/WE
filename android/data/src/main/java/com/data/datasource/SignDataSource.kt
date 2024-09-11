package com.data.datasource

import com.data.model.request.RequestSignUp
import com.data.model.response.ResponseSignUp

interface SignDataSource {

    suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp
}