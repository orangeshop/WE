package com.we.presentation.sign.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.repository.SignRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.LoginParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signRepository: SignRepository
) : ViewModel() {
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