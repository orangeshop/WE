package com.we.presentation.couple.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.repository.CoupleRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import com.we.presentation.couple.model.CoupleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoupleViewModel @Inject constructor(
    private val coupleRepository: CoupleRepository
) : ViewModel() {

    private val _coupleCode = MutableStateFlow(CoupleUiState.CoupleLoading)
    val coupleCode: Flow<CoupleUiState> get() = _coupleCode

    fun getCoupleCode() {
        viewModelScope.launch {
            when (val response = safeApiCall(Dispatchers.IO) {
                coupleRepository.getCoupleCode()
            }) {
                is ApiResult.Success -> {
                    Timber.d("success")
                    response.data.collect{value ->
                        Timber.d("couple code : $value")
                    }
                }
                is ApiResult.Error -> {
                    Timber.d("couple code : fail")
                }
            }

        }
    }
}