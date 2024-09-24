package com.we.presentation.schedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.we.model.ScheduleParam
import com.we.presentation.util.ScheduleRegisterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleRegisterViewModel @Inject constructor(

) : ViewModel() {


    private val _scheduleRegisterParam = MutableStateFlow<ScheduleParam>(ScheduleParam())
    val scheduleRegisterParam: StateFlow<ScheduleParam> get() = _scheduleRegisterParam

    fun setRegisterParam(type: ScheduleRegisterType, content: Any?) {
        when (type) {
            ScheduleRegisterType.DATE -> {
                _scheduleRegisterParam.update {
                    it.copy(
                        date = content.toString()
                    )
                }
            }

            ScheduleRegisterType.PRICE -> {
                if (content != null) {
                    _scheduleRegisterParam.update {
                        it.copy(
                            price = content as Long
                        )
                    }
                }
            }

            ScheduleRegisterType.CONTENT -> {
                _scheduleRegisterParam.update {
                    it.copy(
                        content = content.toString()
                    )
                }
            }

            ScheduleRegisterType.LOCATION -> {
                _scheduleRegisterParam.update {
                    it.copy(
                        location = content.toString()
                    )
                }
            }
        }
    }

    private val _registerButtonActive = MutableSharedFlow<Boolean>(1)
    val registerButtonActive: SharedFlow<Boolean> get() = _registerButtonActive

    fun setRegisterButtonActive() {
        viewModelScope.launch {
            _scheduleRegisterParam.collectLatest {
                val result =
                    it.content.isNotEmpty() && it.location.isNotEmpty() && it.date.isNotEmpty()
                _registerButtonActive.emit(result)
            }
        }
    }

    init {
        setRegisterButtonActive()
    }
}