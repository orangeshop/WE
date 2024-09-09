package com.we.presentation.schedule

import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.ScheduleCalendarAdapter
import com.we.presentation.component.adapter.ScheduleTodoAdapter
import com.we.presentation.databinding.FragmentScheduleBinding
import com.we.presentation.schedule.model.ScheduleUiState
import com.we.presentation.util.CalendarType
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(R.layout.fragment_schedule) {
    private lateinit var scheduleCalendarAdapter: ScheduleCalendarAdapter
    private lateinit var scheduleTodoAdapter: ScheduleTodoAdapter

    override fun initView() {
        initScheduleCalendarAdapter()
        initScheduleTodoAdapter()
        initClickEventListener()
    }


    private fun initScheduleCalendarAdapter() {
        scheduleCalendarAdapter = ScheduleCalendarAdapter()
        binding.apply {
            rvScheduleCalendar.adapter = scheduleCalendarAdapter
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

    private fun initScheduleTodoAdapter() {
        scheduleTodoAdapter = ScheduleTodoAdapter()
        binding.apply {
            rvScheduleTodo.adapter = scheduleTodoAdapter
        }
        scheduleTodoAdapter.submitList(listOf(" ", " ", " ", " "))
    }

    private fun initClickEventListener() {
        binding.apply {
            ivPlus.setOnClickListener {
                navigateDestination(R.id.action_fragment_schedule_to_fragment_schedule_register)
            }
        }
    }
}
