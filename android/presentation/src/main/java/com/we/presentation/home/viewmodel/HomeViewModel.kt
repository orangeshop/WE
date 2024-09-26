package com.we.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import com.we.model.BankData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel(){
    private val _accountList = MutableStateFlow<List<BankData>>(mutableListOf<BankData>())
    val accountList: Flow<List<BankData>> get() =  _accountList

    init {
        setAccountList(arrayListOf(BankData("", "", "", "", "", "", "", "", "", "", "", "","","")))
    }
    private fun setAccountList(list: List<BankData>) {
        _accountList.update { list }
    }
}