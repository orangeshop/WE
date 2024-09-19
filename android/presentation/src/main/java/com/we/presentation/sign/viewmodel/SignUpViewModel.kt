package com.we.presentation.sign.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.model.request.RequestSignUp
import com.data.repository.DataStoreRepository
import com.data.repository.SignRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.LoginParam
import com.we.model.SignUpParam
import com.we.presentation.sign.model.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signRepository: SignRepository
) : ViewModel() {


    private val _signData = MutableStateFlow(SignUpUiState.SignUpLoading)
    val signData: StateFlow<SignUpUiState> get() = _signData

    fun signUp() {
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

    fun singIn() {

        viewModelScope.launch {
            when (val response = safeApiCall(Dispatchers.IO) {
                signRepository.postLogin(
                    LoginParam(
                        "d104test1027@gmail.com", "1234"
                    )
                )
            }) {
                is ApiResult.Success -> {
                    Timber.d("Success " + response.data.first())
                }

                is ApiResult.Error -> {
                    Timber.d("Error")
                }
            }
        }
    }
}