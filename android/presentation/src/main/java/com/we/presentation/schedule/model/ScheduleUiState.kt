package com.we.presentation.schedule.model

import java.time.LocalDate

sealed interface ScheduleUiState {
    data object Loading : ScheduleUiState
    data class CalendarSet(val date: LocalDate, val calendarItem : List<CalendarItem>) : ScheduleUiState
    data class ScheduleSet(
        val calendarItem: List<CalendarItem>,
    ) : ScheduleUiState

}