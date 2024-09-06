package com.we.presentation.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentAccountTransferBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountTransferFragment : BaseFragment<FragmentAccountTransferBinding>(R.layout.fragment_account_transfer) {
    override fun initView() {
        backBtnClickListener()

    }

    private fun backBtnClickListener(){
        binding.apply {
            ivAccountBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}