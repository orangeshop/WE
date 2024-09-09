package com.we.presentation.schedule

import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.ScheduleCalendarAdapter
import com.we.presentation.databinding.FragmentScheduleBinding
import com.we.presentation.schedule.model.ScheduleUiState
import com.we.presentation.util.CalendarType
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(R.layout.fragment_schedule) {
    private lateinit var scheduleCalendarAdapter: ScheduleCalendarAdapter

    override fun initView() {
        initScheduleCalendarAdapter()
    }


    private fun initScheduleCalendarAdapter() {
        scheduleCalendarAdapter = ScheduleCalendarAdapter()
        binding.apply {
            rcScheduleCalendar.adapter = scheduleCalendarAdapter
        }
        scheduleCalendarAdapter.submitList(
            listOf(
                ScheduleUiState.ScheduleSet(CalendarType.AFTER, "1"),
                ScheduleUiState.ScheduleSet(CalendarType.AFTER, "1"),
                ScheduleUiState.ScheduleSet(CalendarType.AFTER, "1"),
                ScheduleUiState.ScheduleSet(CalendarType.AFTER, "1"),
                ScheduleUiState.ScheduleSet(CalendarType.AFTER, "1"),
                ScheduleUiState.ScheduleSet(CalendarType.AFTER, "1"),
                ScheduleUiState.ScheduleSet(CalendarType.AFTER, "1"),
            )
        )
    }
}