package com.we.model

data class ScheduleData(
    val address: String,
    val content: String,
    val coupleId: Int,
    val done: Boolean,
    val price: Int,
    val scheduleId: Int,
    val scheduledTime: String
)