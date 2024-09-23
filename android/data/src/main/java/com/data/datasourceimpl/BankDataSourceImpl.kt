package com.data.datasourceimpl

import com.data.api.BankApi
import com.data.datasource.BankDataSource
import com.data.model.response.ResponseBank
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BankDataSourceImpl @Inject constructor(
    private val bankApi: BankApi

) : BankDataSource {
    override suspend fun getMyAccount(): ResponseBank {
        return bankApi.getMyAccount()
    }
}