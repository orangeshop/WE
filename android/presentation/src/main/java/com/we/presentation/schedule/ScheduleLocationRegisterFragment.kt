package com.we.presentation.schedule

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.presentation.base.BaseDialogFragment
import com.we.presentation.databinding.FragmentScheduleLocationRegisterBinding
import com.we.presentation.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel


@AndroidEntryPoint
class ScheduleLocationRegisterFragment : BaseDialogFragment<FragmentScheduleLocationRegisterBinding>(R.layout.fragment_schedule_location_register) {

    override fun initCreateDialog() = BottomSheetDialog(requireContext(), theme)

    override fun initView(savedInstanceState: Bundle?) {

    }
}