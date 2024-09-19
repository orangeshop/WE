package com.data.repositoryimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.data.repository.DataStoreRepository
import com.data.util.TokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val tokenProvider: TokenProvider
) : DataStoreRepository{

    override val accessToken: Flow<String> = dataStore.data
        .catch { exception ->
            emit(emptyPreferences())
        }
        .map { preferences ->
            preferences[stringPreferencesKey("accessToken")] ?: "defaultAccessToken"
        }

    override val refreshToken: Flow<String> = dataStore.data
        .catch { exception ->
            emit(emptyPreferences())
        }
        .map { preferences ->
            preferences[stringPreferencesKey("refreshToken")] ?: "defaultRefreshToken"
        }

    override suspend fun setAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("accessToken")] = accessToken
        }
        // TokenProvider 관련 코드 제거
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("refreshToken")] = refreshToken
        }
        // TokenProvider 관련 코드 제거
    }
}