package com.we.core.util

import com.data.repository.SignRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


class TokenInterceptor @Inject constructor(private val signRepository: SignRepository): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken: String = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImQxMDR0ZXN0MTAyNkBnbWFpbC5jb20iLCJpYXQiOjE3MjYxOTM0MTQsImV4cCI6MTcyNzQwMzAxNH0.O6pDNhUBISIVyw47awmvOfI9Skqn0LybcRaLlh2Cs_zb3qEP2e5vimutB73-6tCO-5_8BKIo3cyINmZQLo94Rw"
        val request = chain.request().putTokenHeader(accessToken)
        var response: Response = chain.proceed(request)




        return response
    }

    private fun Request.putTokenHeader(accessToken: String): Request {
        return this.newBuilder()
            .addHeader(AUTHORIZATION, "Bearer $accessToken")
            .build()
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}