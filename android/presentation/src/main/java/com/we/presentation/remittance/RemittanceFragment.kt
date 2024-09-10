package com.we.presentation.remittance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.we.presentation.R
import com.we.presentation.account.AccountModalBottomSheet
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentRemittanceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemittanceFragment : BaseFragment<FragmentRemittanceBinding>(R.layout.fragment_remittance) {
    override fun initView() {
        initClickListener()
        accountBottomSheetClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            ivRemittanceBack.setOnClickListener {
                navigatePopBackStack()
            }

            tvInputComplete.setOnClickListener {
                navigateDestination(R.id.action_remittanceFragment_to_remittanceCheckFragment)
            }
        }
    }

    private fun accountBottomSheetClickListener(){
        binding.apply {
            flChooseBank.setOnClickListener {
                val modal = AccountModalBottomSheet()
                modal.show(parentFragmentManager, modal.tag)
            }
        }
    }
}