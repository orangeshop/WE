package com.we.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.repository.CoupleRepository
import com.data.util.ApiResult
import com.we.presentation.home.model.InvitationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val coupleRepository: CoupleRepository
) : ViewModel() {

    private val _invitationUiState =
        MutableStateFlow<InvitationUiState>(InvitationUiState.InvitationEmpty)
    val invitationUiState: StateFlow<InvitationUiState> get() = _invitationUiState

    fun setInvitationUiState(value: InvitationUiState) {
        _invitationUiState.update { value }
    }

    fun getInvitation() {
        viewModelScope.launch {
            coupleRepository.getInvitation().collectLatest {
                when (it) {
                    is ApiResult.Success -> {
                        setInvitationUiState(InvitationUiState.InvitationSuccess(it.data))
                        Timber.tag("초대장").d("성공 ${it.data}")
                    }

                    is ApiResult.Error -> {
                        Timber.tag("초대장").d("에러 ${it.exception}")
                    }
                }
            }
        }
    }

    init {
        getInvitation()
    }

}