package com.we.presentation.sign.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.repository.SignRepository
import com.data.util.ApiResult
import com.we.model.SignParam
import com.we.presentation.sign.model.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signRepository: SignRepository
) : ViewModel() {

    private val _signInParam = MutableStateFlow<SignParam>(SignParam())
    val signInParam: StateFlow<SignParam> get() = _signInParam

    private val _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.SignInLoading)
    val signInUiState: StateFlow<SignInUiState> get() = _signInUiState

    fun setSignInUiState(state: SignInUiState) {
        _signInUiState.update { state }
    }

    fun singIn() {

        viewModelScope.launch {
            signRepository.postLogin(signInParam.value).collectLatest {
                when (it) {
                    is ApiResult.Success -> {
                        setSignInUiState(SignInUiState.SignInSuccess(it.data.coupleJoined))
                        Timber.tag("로그인").d("성공")
                    }

                    is ApiResult.Error -> {
                        setSignInUiState(SignInUiState.SignInError(it.exception.toString()))
                        Timber.tag("로그인").d("에러 ${it.exception}")
                    }
                }
            }
        }
    }
}