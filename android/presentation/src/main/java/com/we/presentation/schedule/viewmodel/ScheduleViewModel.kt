package com.we.presentation.schedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.we.presentation.schedule.model.CalendarItem
import com.we.presentation.schedule.model.ScheduleEventState
import com.we.presentation.schedule.model.ScheduleUiState
import com.we.presentation.util.CalendarType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(

) : ViewModel() {

    val date = MutableStateFlow<LocalDate>(LocalDate.now())


    private val _scheduleUiState =
        MutableStateFlow<ScheduleUiState>(ScheduleUiState.CalendarSet(date.value, listOf()))
    val scheduleUiState: StateFlow<ScheduleUiState> get() = _scheduleUiState


    private val _scheduleEventState = MutableSharedFlow<ScheduleEventState>()
    val scheduleEventState: SharedFlow<ScheduleEventState> get() = _scheduleEventState

    fun setScheduleEvent(event: ScheduleEventState) {
        viewModelScope.launch {
            _scheduleEventState.emit(event)
        }
    }

    init {
        checkDate()
    }

    fun plusMinusMonth(type: Boolean) { // true -> plus, false -> minus
        if (type) {
            date.update {
                it.plusMonths(1)
            }
        } else {
            date.update {
                it.minusMonths(1)
            }
        }
    }

    fun addDateList(date: LocalDate): List<CalendarItem> {
        val yearMonth = YearMonth.from(date)
        val firstOfMonth = yearMonth.atDay(1)
        val daysInMonth = yearMonth.lengthOfMonth()
        val now = LocalDate.now()
        // 첫 번째 날의 요일 (1: 월요일, ..., 7: 일요일)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        // 이전 달 정보
        val previousMonth = yearMonth.minusMonths(1)
        val previousMonthDays = previousMonth.lengthOfMonth()


        val totalDays = 35

        // 이전 달의 날짜 수
        val daysFromPrevMonth = dayOfWeek % 7 // 일요일이면 0일 추가
        // 다음 달의 날짜 수
        val daysFromNextMonth = totalDays - daysFromPrevMonth - daysInMonth

        val days = mutableListOf<CalendarItem>()

        // 이전 달의 날짜 추가
        if (daysFromPrevMonth > 0) {
            val startPrevMonth = previousMonth.atDay(previousMonthDays - daysFromPrevMonth + 1)
            days += (0 until daysFromPrevMonth).map { i ->
                val currentDate = startPrevMonth.plusDays(i.toLong())
                CalendarItem(
                    date = currentDate,
                    calendarType = CalendarType.BEFORE
                )
            }
        }

        // 현재 달의 날짜 추가
        days += (0 until daysInMonth).map { i ->
            val currentDate = firstOfMonth.plusDays(i.toLong())
            val type = if(now.dayOfMonth == currentDate.dayOfMonth)CalendarType.TODAY else CalendarType.CURRENT
            CalendarItem(
                date = currentDate,
                calendarType = type
            )
        }

        // 다음 달의 날짜 추가
        if (daysFromNextMonth > 0) {
            val nextMonth = yearMonth.plusMonths(1)
            days += (0 until daysFromNextMonth).map { i ->
                val currentDate = nextMonth.atDay(i + 1)
                CalendarItem(
                    date = currentDate,
                    calendarType = CalendarType.AFTER
                )
            }
        }

        return days
    }


    private fun checkDate() {
        viewModelScope.launch {
            date.collectLatest { date ->
                _scheduleUiState.update {
                    ScheduleUiState.CalendarSet(date, addDateList(date))
                }

            }
        }
    }

}