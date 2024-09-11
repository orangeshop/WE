package com.data.datasourceimpl

import com.data.api.SignApi
import com.data.datasource.SignDataSource
import com.data.model.request.RequestSignUp
import com.data.model.response.ResponseSignUp
import javax.inject.Inject

class SignDataSourceImpl @Inject constructor(
    private val signApi: SignApi
) : SignDataSource {

    override suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp {
        return signApi.postSignUp(requestSignUp)
    }
}