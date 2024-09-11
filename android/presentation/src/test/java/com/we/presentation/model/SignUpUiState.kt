package com.we.presentation.sign.model

sealed interface SignUpUiState {

    data object SignUpSuccess : SignUpUiState

    data class SignParam(
        val email: String = "",
        val password: String = "",
        val nickname: String = ""
    ) {
         val signUpValid: Boolean
            get() {
                return email.isNotEmpty() && password.isNotEmpty() && nickname.isNotEmpty()
            }
    }

    data object SignUpLoading : SignUpUiState

}