package com.we.presentation.couple.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.repository.CoupleRepository
import com.data.util.ApiResult
import com.data.util.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoupleViewModel @Inject constructor(
    private val coupleRepository: CoupleRepository
) : ViewModel() {


    fun getCoupleCode() {
        viewModelScope.launch {
            when (val response = safeApiCall(Dispatchers.IO) {
                coupleRepository.getCoupleCode()
            }) {
                is ApiResult.Success -> {
                    Timber.d("couple code : ${response.data}")
                }
                is ApiResult.Error -> {
                    Timber.d("couple code : fail")
                }
            }

        }
    }
}