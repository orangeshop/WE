package com.data.api

import com.data.model.response.ResponseSchedule
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {

    @GET("schedule/view")
    suspend fun getSchedule(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): ResponseSchedule

}