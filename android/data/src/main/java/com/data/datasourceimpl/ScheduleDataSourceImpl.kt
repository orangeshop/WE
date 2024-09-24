package com.data.datasourceimpl

import com.data.api.ScheduleApi
import com.data.datasource.ScheduleDataSource
import com.data.model.response.ResponseSchedule
import javax.inject.Inject

class ScheduleDataSourceImpl @Inject constructor(
    private val scheduleApi: ScheduleApi
) : ScheduleDataSource {

    override suspend fun getSchedule(
        year: Int,
        month: Int
    ): ResponseSchedule {
        return scheduleApi.getSchedule(year, month)
    }
}