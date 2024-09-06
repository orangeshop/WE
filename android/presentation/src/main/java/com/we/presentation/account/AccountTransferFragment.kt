package com.we.presentation.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentAccountTransferBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountTransferFragment : BaseFragment<FragmentAccountTransferBinding>(R.layout.fragment_account_transfer) {
    override fun initView() {
        backBtnClickListener()
        autoFocusMove()
    }

    private fun autoFocusMove(){
        binding.apply {
            etTransferNumber1.addTextChangedListener {
                if("1".equals(it.toString())){
                    etTransferNumber2.requestFocus()
                }
            }
        }
    }

    private fun backBtnClickListener(){
        binding.apply {
            ivAccountBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}