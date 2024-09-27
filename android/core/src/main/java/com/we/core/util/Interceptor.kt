package com.we.core.util

import com.data.repository.SignRepository
import com.data.util.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


class TokenInterceptor @Inject constructor(
    private val signRepository: SignRepository,
    private val tokenProvider: TokenProvider
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken: String? = tokenProvider.getAccessToken()
        val request = chain.request().putTokenHeader(accessToken)
        val response: Response = chain.proceed(request)
        return response
    }

    private fun Request.putTokenHeader(accessToken: String?): Request {
        val token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImJveUB0ZXN0LmNvbSIsImlhdCI6MTcyNzMyMzQwMywiZXhwIjoxNzI4NTMzMDAzfQ.14rMAw02erPvj5eZC4PKCDloIokpfNlfgl7RGwAKiM4tLRAB68oYgWcaUJIShiY3V-M10axOvsMLUxHa0MNKKQ"

        return this.newBuilder()
            .addHeader(AUTHORIZATION, "Bearer $accessToken")
            .build()
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}