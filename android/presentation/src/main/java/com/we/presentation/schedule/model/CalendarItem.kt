package com.we.presentation.schedule.model

import com.we.presentation.util.CalendarType
import java.time.LocalDate

data class CalendarItem(
    val date : LocalDate,
    val calendarType : CalendarType
)
