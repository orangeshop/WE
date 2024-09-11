package com.we.presentation.sign.model

sealed interface SignUpUiState {

    data object SignUpSuccess : SignUpUiState

    data class SignData(val data : String) : SignUpUiState

    data object SignUpLoading : SignUpUiState

}