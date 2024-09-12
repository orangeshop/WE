package com.we.presentation.couple.model

sealed interface CoupleUiState {
    data object CoupleSuccess : CoupleUiState
    data class CoupleData(val data : String) : CoupleUiState
    data object CoupleLoading : CoupleUiState
}