package com.we.presentation.transfer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.model.request.RequestTransfer
import com.data.repository.BankRepository
import com.data.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class TransferViewModel @Inject constructor(
    private val bankRepository: BankRepository
) : ViewModel() {


    private val _easyPassword = MutableStateFlow<MutableList<String>>(mutableListOf())
    val easyPassword: StateFlow<List<String>> get() = _easyPassword


    private val _money = MutableStateFlow<String>("")
    val money: StateFlow<String> get() = _money

    fun setMoney(value: String) {
        _money.update { value }
    }

    private val _brideType = MutableStateFlow<Boolean>(false)
    val brideType: StateFlow<Boolean> get() = _brideType

    fun setBrideType(value: Boolean) {
        _brideType.update { value }
    }


    fun addRemoveEasyPassword(type: Boolean, value: String = "") { //간편 비밀 번호 추가 삭제
        _easyPassword.update {
            it.toMutableList().also { list ->
                if (type) list.add(value) else list.removeLast()
            }
        }
    }

    fun clearEasyPasswordAndCheck() { // 간편 비밀번호 초기화
        _easyPassword.update {
            mutableListOf()
        }
    }

    private val _transferSuccess = MutableSharedFlow<Boolean>()
    val transferSuccess: SharedFlow<Boolean> get() = _transferSuccess

    fun postTransfer(ledgerId: Int) {
        viewModelScope.launch {
            bankRepository.postTransfer(
                RequestTransfer(
                    ledgerId = ledgerId,
                    depositAccountNo = null,
                    transactionBalance = money.value.toInt(),
                    isBride = brideType.value,
                    pin = easyPassword.value.joinToString(""),
                    withdrawalAccountNo = null
                )
            ).collectLatest {
                when (it) {
                    is ApiResult.Success -> {
                        _transferSuccess.emit(true)
                    }

                    is ApiResult.Error -> {
                        Timber.tag("송금 이체").d("${it.exception}")
                        _transferSuccess.emit(false)
                    }
                }
            }
        }

    }


}