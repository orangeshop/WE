package com.data.datasource

import com.data.model.request.RequestLogin
import com.data.model.request.RequestSignUp
import com.data.model.response.ResponseSignIn
import com.data.model.response.ResponseSignUp

interface SignDataSource {

    suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp

    suspend fun postLogin(requestLogin: RequestLogin): ResponseSignIn
}