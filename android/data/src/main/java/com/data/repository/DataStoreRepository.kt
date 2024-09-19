package com.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    val accessToken: Flow<String>
    val refreshToken: Flow<String>
    suspend fun setAccessToken(accessToken: String)
    suspend fun setRefreshToken(refreshToken: String)
}