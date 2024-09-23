package com.data.datasource

import com.data.model.response.ResponseBank

interface BankDataSource {
    suspend fun getMyAccount(): ResponseBank
}