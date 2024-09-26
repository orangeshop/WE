package com.data.repository

import com.data.util.ApiResult
import com.we.model.ScheduleData
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    fun getSchedule(year : Int, month : Int) : Flow<ApiResult<List<ScheduleData>>>
}