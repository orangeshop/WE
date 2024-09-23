package com.we.presentation.account.viewmodel

import androidx.lifecycle.ViewModel
import com.we.presentation.account.util.BankList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(

) : ViewModel(){
    private val _bankList = MutableStateFlow<List<BankList>>(mutableListOf())
    val bankList: Flow<List<BankList>> get() =  _bankList

    private val _chooseBank = MutableStateFlow(BankList())
    val chooseBank: Flow<BankList> get() = _chooseBank


    init {
        setBankList(BankList.bankLs)
    }

    private fun setBankList(list: List<BankList>) {
        _bankList.update { list }
    }

    fun setChooseBank(bank: BankList){
        _chooseBank.update { it.copy(bankName = bank.bankName, bankIcList = bank.bankIcList) }
    }


}