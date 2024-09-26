package com.data.repositoryimpl

import com.data.datasource.ScheduleDataSource
import com.data.mapper.toEntity
import com.data.repository.ScheduleRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.ScheduleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleDataSource: ScheduleDataSource
) : ScheduleRepository {

    override fun getSchedule(
        year: Int,
        month: Int
    ): Flow<ApiResult<List<ScheduleData>>> {
        return flow {
            emit(safeApiCall {
                scheduleDataSource.getSchedule(year, month).toEntity()
            })
        }
    }
}