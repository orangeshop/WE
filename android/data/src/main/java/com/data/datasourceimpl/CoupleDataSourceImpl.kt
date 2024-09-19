package com.data.datasourceimpl

import com.data.api.CoupleApi
import com.data.datasource.CoupleDataSource
import com.data.model.request.RequestCouple
import com.data.model.response.ResponseCouple
import javax.inject.Inject

class CoupleDataSourceImpl @Inject constructor(
    private val coupleApi: CoupleApi
): CoupleDataSource {
    override suspend fun getCoupleCode(): ResponseCouple {
        return coupleApi.getCoupleCode()
    }
}