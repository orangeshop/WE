package com.we.presentation.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.AccountBankChooseAdapter
import com.we.presentation.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account) {
    override fun initView() {
        backBtnClickListener()
        accountBottomSheetClickListener()
    }

    private fun backBtnClickListener(){
        binding.apply {
            ivAccountBack.setOnClickListener {
                navigatePopBackStack()
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