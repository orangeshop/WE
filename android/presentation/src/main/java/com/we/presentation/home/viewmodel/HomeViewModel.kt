package com.we.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import com.data.repository.BankRepository
import com.we.model.BankData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bankRepository: BankRepository
) : ViewModel(){
    private val _accountList = MutableStateFlow<List<BankData>>(mutableListOf<BankData>())
    val accountList: Flow<List<BankData>> get() =  _accountList

    init {

        //---- 테스트 용도로 넣은 값 입니다. 추후 삭제될 예정입니다.
        setAccountList(arrayListOf(BankData("1", "1", "1", "1", "1", "1", "1", "", "1", "1", "1", "1","1","1")))


        setAccountList(arrayListOf(BankData("", "", "", "", "", "", "", "", "", "", "", "","","")))
    }
    private fun setAccountList(list: List<BankData>) {
        _accountList.update { oldlist ->
            oldlist + list
        }
    }

//    private fun setCouple
}