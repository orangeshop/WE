package com.we.presentation.schedule

import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentScheduleRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleRegisterFragment :
    BaseFragment<FragmentScheduleRegisterBinding>(R.layout.fragment_schedule_register) {

    override fun initView() {
        initClickEventListener()
    }

    private fun initClickEventListener() {
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }
            btnLocation.setOnClickListener {
                navigateDestination(R.id.action_scheduleRegisterFragment_to_locationRegisterFragment)
            }
        }
    }
}
