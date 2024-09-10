package com.we.presentation.remittance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentRemittanceCheckBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemittanceCheckFragment : BaseFragment<FragmentRemittanceCheckBinding>(R.layout.fragment_remittance_check) {
    override fun initView() {
        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            tvRemittanceCheck.setOnClickListener {
                navigateDestination(R.id.action_remittanceCheckFragment_to_easyPasswordRegisterFragment)
            }

            ivRemittanceBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}