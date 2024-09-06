package com.we.presentation.schedule

import com.we.model.MyClass
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentScheduleBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(R.layout.fragment_schedule) {
    override fun initView() {
        val myClass = MyClass("","")
    }
}