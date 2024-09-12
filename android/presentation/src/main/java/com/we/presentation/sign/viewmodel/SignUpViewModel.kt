package com.we.presentation.sign.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.repository.SignRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.SignUpParam
import com.we.presentation.sign.model.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signRepository: SignRepository
) : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> get() = _email

    private val _emailName = MutableStateFlow<String>("")
    val emailName: StateFlow<String> get() = _emailName

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> get() = _password

    private val _nickname = MutableStateFlow<String>("")
    val nickname: StateFlow<String> get() = _nickname


    private val _nextButtonActivate = MutableSharedFlow<Boolean>(1)
    val nextButtonActivate: SharedFlow<Boolean> get() = _nextButtonActivate

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setEmailName(emailName: String) {
        _emailName.value = emailName
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setNickName(nickname: String) {
        _nickname.value = nickname
    }

    private fun checkSignNext() {
        combine(
            email,
            emailName,
            nickname,
            password,
        ) { email, nickname, emailName, password ->
            email.isNotEmpty() && emailName.isNotEmpty() && nickname.isNotEmpty() && password.isNotEmpty()
        }.onEach { isEnabled ->
            _nextButtonActivate.emit(isEnabled)
        }.launchIn(viewModelScope)
    }

    init {
        checkSignNext()
    }

    private val _signUpUiState = MutableStateFlow(SignUpUiState.SignUpEmpty)
    val signUpUiState: StateFlow<SignUpUiState> get() = _signUpUiState

    fun signUp(email: String, password: String, nickname: String) {
        viewModelScope.launch {
            when (val response = safeApiCall(Dispatchers.IO) {
                signRepository.postSignUp(
                    SignUpParam(
                        "", "", ""
                    )
                )
            }) {
                is ApiResult.Success -> {

                }

                is ApiResult.Error -> {

                }
            }
        }
    }
}