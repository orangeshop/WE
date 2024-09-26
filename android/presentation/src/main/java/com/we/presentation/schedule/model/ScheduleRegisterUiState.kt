package com.we.presentation.schedule.model

sealed interface ScheduleRegisterUiState {

    data object RegisterEmpty : ScheduleRegisterUiState

    data object RegisterSuccess : ScheduleRegisterUiState

    data class RegisterError(val error: String) : ScheduleRegisterUiState
}