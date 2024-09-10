package com.we.presentation.accountcheck

import com.we.presentation.R
import com.we.presentation.account.AccountModalBottomSheet
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.AccountCheckAdapter
import com.we.presentation.databinding.FragmentAccountCheckBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountCheckFragment : BaseFragment<FragmentAccountCheckBinding>(R.layout.fragment_account_check) {
    override fun initView() {
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        val adapter = AccountCheckAdapter()
        val test = arrayListOf("1","2","3")

        binding.apply {
            rvAccountCheckList.adapter = adapter
            adapter.submitList(test)
        }
    }


    private fun initClickListener() {
        binding.apply {
            ivAccountBack.setOnClickListener {
                navigatePopBackStack()
            }

            llAccountCheckMenu.setOnClickListener {
                val modal = AccountCheckBottomSheet()
                modal.show(parentFragmentManager, modal.tag)
            }
        }
    }
}