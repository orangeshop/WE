package com.we.presentation.sign.model

interface SignInUiState {

    data class SignInSuccess(val coupleJoined: Boolean) : SignInUiState

    data class SignInError(val error: String) : SignInUiState

    data object SignInLoading : SignInUiState

}