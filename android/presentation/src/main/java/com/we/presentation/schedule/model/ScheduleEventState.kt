package com.we.presentation.schedule.model

sealed interface ScheduleEventState {

    data object NextMonth : ScheduleEventState
    data object BeforeMonth : ScheduleEventState
    data class ClickDate(val date: String) : ScheduleEventState

}