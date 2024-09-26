package com.data.datasource

import com.data.model.response.ResponseSchedule

interface ScheduleDataSource {

    suspend fun getSchedule(
        year: Int,
        month: Int
    ): ResponseSchedule
}