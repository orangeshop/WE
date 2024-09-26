package com.data.mapper

import com.data.model.response.ResponseSchedule
import com.we.model.ScheduleData

fun ResponseSchedule.toEntity(): List<ScheduleData> {
    return this.scheduleList.map {
        ScheduleData(
            address = it.address,
            scheduleId = it.schedule_id,
            scheduledTime = it.scheduled_time,
            price = it.price,
            done = it.done,
            coupleId = it.couple_id,
            content = it.content
        )
    }
}