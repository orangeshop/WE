package com.we.presentation.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.we.presentation.R
import com.we.presentation.account.viewmodel.AccountViewModel
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentAccountTransferBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AccountTransferFragment :
    BaseFragment<FragmentAccountTransferBinding>(R.layout.fragment_account_transfer) {
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun initView() {
        backBtnClickListener()
        autoFocusMove()
        initAuthCode()
    }

    private fun initAuthCode() {
        binding.apply {
            tvRegisterAccount.setOnClickListener {
                accountViewModel.setAuthCode(
                    etTransferNumber1.text.toString(),
                    etTransferNumber2.text.toString(),
                    etTransferNumber3.text.toString(),
                    etTransferNumber4.text.toString()
                )
                accountViewModel.getAuthCodeCertified() { result ->
                    if (result == true) {
                        Toast.makeText(requireContext(), "성공", Toast.LENGTH_SHORT).show()
                        navigatePopBackStack()
                    } else {
                        Toast.makeText(requireContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun autoFocusMove() {
        binding.apply {
            etTransferNumber1.addTextChangedListener {
                if (etTransferNumber1.text.isNotBlank()) {
                    etTransferNumber2.requestFocus()
                }
            }

            etTransferNumber2.addTextChangedListener {
                if (etTransferNumber2.text.isNotBlank()) {
                    etTransferNumber3.requestFocus()
                }
            }

            etTransferNumber3.addTextChangedListener {
                if (etTransferNumber3.text.isNotBlank()) {
                    etTransferNumber4.requestFocus()
                }
            }
        }
    }

    private fun backBtnClickListener() {
        binding.apply {
            ivAccountBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}