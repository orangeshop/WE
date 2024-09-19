package com.we.presentation.couple.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        getCoupleCode()
    }

    fun setCode(coupleCode: String) {
        _coupleCode.update { it.copy(coupleCode) }
    }

    fun getCoupleCode() {
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
}