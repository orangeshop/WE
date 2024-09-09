package com.we.presentation.schedule.model

import com.we.presentation.util.CalendarType

sealed class ScheduleUiState {
    data object Loading : ScheduleUiState()
    data class ScheduleSet(val calendarType: CalendarType, val date: String) : ScheduleUiState()
}