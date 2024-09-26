package com.we.presentation.schedule

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.ScheduleCalendarAdapter
import com.we.presentation.component.adapter.ScheduleTodoAdapter
import com.we.presentation.databinding.FragmentScheduleBinding
import com.we.presentation.schedule.model.ScheduleUiState.CalendarSet
import com.we.presentation.schedule.model.ScheduleUiState.Loading
import com.we.presentation.schedule.model.ScheduleUiState.ScheduleSet
import com.we.presentation.schedule.viewmodel.ScheduleViewModel
import com.we.presentation.util.toYearMonth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(R.layout.fragment_schedule) {

    private val scheduleViewModel: ScheduleViewModel by viewModels()

    private lateinit var scheduleCalendarAdapter: ScheduleCalendarAdapter
    private lateinit var scheduleTodoAdapter: ScheduleTodoAdapter

    override fun initView() {
        initScheduleCalendarAdapter()
        initScheduleTodoAdapter()
        initClickEventListener()
        observeScheduleUiState()
    }


    private fun initScheduleCalendarAdapter() {
        scheduleCalendarAdapter = ScheduleCalendarAdapter()
        binding.apply {
            rvScheduleCalendar.adapter = scheduleCalendarAdapter
        }

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
                navigateDestination(R.id.action_scheduleFragment_to_schedule_register_nav_graph)
            }
            ivScheduleRight.setOnClickListener {
                scheduleViewModel.plusMinusMonth(true)
            }
            ivScheduleLeft.setOnClickListener {
                scheduleViewModel.plusMinusMonth(false)
            }

        }
    }

    private fun observeScheduleUiState() {
        scheduleViewModel.scheduleUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is Loading -> {

                    }

                    is ScheduleSet -> {

                    }

                    is CalendarSet -> {
                        val date = it.date.toYearMonth()
                        binding.apply {
                            year = getString(R.string.schedule_year, date.first)
                            month = getString(R.string.schedule_month, date.second)
                        }
                        scheduleCalendarAdapter.submitList(it.calendarItem)
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
