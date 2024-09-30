package com.we.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.model.request.RequestRegisterCoupleAccount
import com.data.model.request.RequestRegisterPriorAccount
import com.data.repository.BankRepository
import com.data.util.ApiResult
import com.we.model.BankData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bankRepository: BankRepository
) : ViewModel(){
    private val _accountList = MutableStateFlow<List<BankData>>(mutableListOf<BankData>())
    val accountList: StateFlow<List<BankData>> get() =  _accountList

    init {
        setAccountList(arrayListOf(BankData("", "", "", "", "", "", "", "", "", "", "", "","","")))
        getAccountList()
    }

    fun getAccountList() {
        viewModelScope.launch {
            bankRepository.getMyAccount().collectLatest {
                when(it){
                    is ApiResult.Success -> {
                        setAccountList(it.data)
                        Timber.d("bank load 성공 " + it.data)
                    }
                    is ApiResult.Error -> {
                        Timber.d("bank load fail " + it.exception.message)
                    }
                }
            }
        }
    }

    private fun setAccountList(list: List<BankData>) {
        _accountList.update { oldList ->
            (list + oldList)
                .distinctBy { it.accountNo } // 중복 제거 기준 설정
        }
    }


    fun postPriorAccount(accountNo : String){
        val request = RequestRegisterPriorAccount(accountNo = accountNo)
        viewModelScope.launch {
            bankRepository.postPriorAccount(request).collectLatest {
                when(it){
                    is ApiResult.Success -> {
                        Timber.d("bank load 성공 " + it.data)
                    }
                    is ApiResult.Error -> {
                        Timber.d("bank load fail " + it.exception.message)
                    }
                }
            }
        }
    }

    fun postCoupleAccount(accountNo : String, bankName : String){

        val request = RequestRegisterCoupleAccount(accountNo = accountNo, bankName = bankName)

        viewModelScope.launch {
            bankRepository.postCoupleAccount(request).collectLatest {
                when (it) {
                    is ApiResult.Success -> {
                        Timber.d("bank load 성공 " + it.data)
                    }

                    is ApiResult.Error -> {
                        Timber.d("bank load fail " + it.exception.message)
                    }
                }
            }
        }
    }


}