package com.we.presentation.remittance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.model.request.RequestTransfer
import com.data.repository.BankRepository
import com.data.util.ApiResult
import com.we.presentation.account.util.BankList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RemittanceViewModel @Inject constructor(
    private val bankRepository: BankRepository
) : ViewModel() {

    private val _chooseBank = MutableStateFlow(BankList(0, ""))
    val chooseBank: Flow<BankList> get() = _chooseBank

    private val _accountNumber = MutableStateFlow("")
    val accountNumber: Flow<String> get() = _accountNumber

    private val _money = MutableStateFlow("")
    val money: Flow<String> get() = _money

    fun setMoney(money: String) {
        _money.update { money }
    }

    fun setAccountNumber(number: String) {
        _accountNumber.update { number }
    }

    fun setChooseBank(bank: BankList) {
        _chooseBank.update { bank }
    }

//    private fun postTransfer() {
//        viewModelScope.launch {
//            bankRepository.postTransfer(
//                RequestTransfer(
//
//                )
//            )
//                .collectLatest {
//                    when (it) {
//                        is ApiResult.Success -> {
//                            Timber.d("Authcode : success ${it}")
//
//                        }
//
//                        is ApiResult.Error -> {
//                            Timber.d("Authcode : fail")
//                        }
//                    }
//                }
//        }
//    }
}