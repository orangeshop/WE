package com.we.presentation.couple

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.couple.viewmodel.CoupleViewModel
import com.we.presentation.databinding.FragmentCoupleBinding
import com.we.presentation.util.ScheduleRegisterType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class CoupleFragment : BaseFragment<FragmentCoupleBinding>(R.layout.fragment_couple) {

    private val coupleViewModel: CoupleViewModel by viewModels()

    override fun initView() {
        initClickEventListener()
        initCoupleCode()
    }

    private fun initCoupleCode() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                coupleViewModel.coupleCode.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .onEach {
                        binding.tvCoupleCode.text = it.code
                    }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun initClickEventListener() {
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }

            tvInputComplete.setOnClickListener {
                coupleViewModel.setCoupleSuccessCode(etInputCouple.text.toString())
                coupleViewModel.postCouple() {
                    when (it) {
                        true -> {
                            Toast.makeText(requireContext(), "매칭 성공", Toast.LENGTH_SHORT).show()
                        }

                        false -> {
                            Toast.makeText(requireContext(), "매칭 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            tvInputDate.setOnClickListener {
//                DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
//                    val selectedDate = Calendar.getInstance()
//                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
//                    val formattedDate = dateFormat.format(selectedDate.time)
//                    scheduleRegisterViewModel.setRegisterParam(ScheduleRegisterType.DATE, formattedDate)
//                }, year, month, day).show()
            }
        }
    }
}