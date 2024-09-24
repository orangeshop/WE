package com.we.presentation.schedule

import android.app.DatePickerDialog
import androidx.core.widget.addTextChangedListener
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentScheduleRegisterBinding
import com.we.presentation.schedule.viewmodel.ScheduleRegisterViewModel
import com.we.presentation.util.ScheduleRegisterType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ScheduleRegisterFragment :
    BaseFragment<FragmentScheduleRegisterBinding>(R.layout.fragment_schedule_register) {

    private val scheduleRegisterViewModel: ScheduleRegisterViewModel by hiltNavGraphViewModels(R.id.schedule_register_nav_graph)

    override fun initView() {
        initClickEventListener()
        observeRegisterActive()
        observeContentPrice()
        observeScheduleRegisterParam()
    }

    private fun initClickEventListener() {
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }
            btnLocation.setOnClickListener {
                navigateDestination(R.id.action_scheduleRegisterFragment_to_locationRegisterFragment)
            }
            tvScheduleDateValue.setOnClickListener {
                showDatePicker()
            }
        }
    }

    private fun observeContentPrice() {
        binding.etScheduleContent.addTextChangedListener {
            scheduleRegisterViewModel.setRegisterParam(ScheduleRegisterType.CONTENT, it.toString())
        }
        binding.etSchedulePrice.addTextChangedListener {
            val data = if (it?.isNotEmpty() == true) {
                it.toString().toLong()
            } else {
                null
            }
            scheduleRegisterViewModel.setRegisterParam(ScheduleRegisterType.PRICE, data)
        }
    }


    private fun showDatePicker() {
        val currentDateTime = Calendar.getInstance()
        val selectedDateString = binding.tvScheduleDateValue.text.toString()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val calendar = if (selectedDateString.isNotEmpty() && selectedDateString != "일정을 선택해주세요") {
            try {
                Calendar.getInstance().apply {
                    time = dateFormat.parse(selectedDateString) ?: Date()
                }
            } catch (e: Exception) {
                currentDateTime
            }
        } else {
            currentDateTime
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)
            val formattedDate = dateFormat.format(selectedDate.time)
            scheduleRegisterViewModel.setRegisterParam(ScheduleRegisterType.DATE, formattedDate)
        }, year, month, day).show()
    }

    private fun observeScheduleRegisterParam() {
        scheduleRegisterViewModel.scheduleRegisterParam.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                binding.apply {
                    tvScheduleDateValue.text = it.date
                    tvScheduleLocationValue.text = it.location
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun observeRegisterActive() {
        scheduleRegisterViewModel.registerButtonActive.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                binding.btnRegister.apply {
                    isSelected = it
                    isEnabled = it
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }
}
