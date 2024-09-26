package com.we.presentation.sign.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.repository.SignRepository
import com.data.util.ApiResult
import com.data.util.TokenProvider
import com.data.util.safeApiCall
import com.we.model.LoginParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signRepository: SignRepository,
    private val tokenProvider: TokenProvider
) : ViewModel() {

    private val _signInParam = MutableStateFlow<LoginParam>(LoginParam())
    val signInParam: StateFlow<LoginParam> get() = _signInParam

    fun singIn(onResult: (Boolean) -> Unit) {

        viewModelScope.launch {
            signRepository.postLogin(signInParam.value).collectLatest {
                when (it) {
                    is ApiResult.Success -> {
                        Timber.d("Success " + it.data)
                        tokenProvider.saveAccessToken(it.data.accessToken)
                        onResult(true)
                    }

                    is ApiResult.Error -> {
                        Timber.d("Error")
                        onResult(false)
                    }
                }
            }
        }
    }
}