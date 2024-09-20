package com.we.presentation.couple.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.model.request.RequestCouple
import com.data.repository.CoupleRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.model.CoupleData
import com.we.presentation.couple.model.CoupleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoupleViewModel @Inject constructor(
    private val coupleRepository: CoupleRepository
) : ViewModel() {

    private val _coupleCodeUiState = MutableStateFlow<CoupleUiState>(CoupleUiState.CoupleEmpty)
    val coupleCodeUiState: Flow<CoupleUiState> get() = _coupleCodeUiState

    fun setCoupleCodeUiState(value: CoupleUiState) {
        _coupleCodeUiState.value = value
    }

    private val _coupleCode = MutableStateFlow<CoupleData>(CoupleData(""))
    val coupleCode: Flow<CoupleData> get() = _coupleCode

    private val _coupleSuccessCode = MutableStateFlow<RequestCouple>(RequestCouple(""))
    val coupleSuccessCode: Flow<RequestCouple> get() = _coupleSuccessCode

    init {
        getCoupleCode()
    }

    private fun setCode(coupleCode: String) {
        _coupleCode.update { it.copy(coupleCode) }
    }

    fun setCoupleSuccessCode(coupleSuccessCode: String) {
        _coupleSuccessCode.update { it.copy(coupleSuccessCode) }
    }

    private fun getCoupleCode() {
        viewModelScope.launch {
            coupleRepository.getCoupleCode().collectLatest {
                when (it) {
                    is ApiResult.Success -> {
                        Timber.d("success " + it.data.code)
                        setCode(it.data.code)
                    }

                    is ApiResult.Error -> {
                        Timber.d("couple code : fail")
                    }
                }
            }
        }
    }

    fun postCouple(onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            coupleRepository.postCouple(requestCouple = coupleSuccessCode.first()).collectLatest {
                when (it) {
                    is ApiResult.Success -> {
                        Timber.d("success " + it.data.coupleId)
                        onResult(true)
                    }

                    is ApiResult.Error -> {
                        Timber.d("couple match : fail")
                        onResult(false)
                    }
                }

            }
        }
    }
}