package com.we.presentation.accountcheck

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.presentation.R
import com.we.presentation.account.AccountModalBottomSheet
import com.we.presentation.accountcheck.viewmodel.AccountCheckViewModel
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.AccountCheckAdapter
import com.we.presentation.databinding.FragmentAccountCheckBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class AccountCheckFragment : BaseFragment<FragmentAccountCheckBinding>(R.layout.fragment_account_check) {

    private val accountCheckViewModel : AccountCheckViewModel by viewModels()

    override fun initView() {
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        accountCheckViewModel.transactionListLoading("0012181503379982")
        val adapter = AccountCheckAdapter()

        binding.apply {
            rvAccountCheckList.adapter = adapter

        }

        accountCheckViewModel.transactionList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                Timber.d("list $it")
                adapter.submitList(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }


    private fun initClickListener() {
        binding.apply {
            ivAccountBack.setOnClickListener {
                navigatePopBackStack()
            }

//            llAccountCheckMenu.setOnClickListener {
//                val modal = AccountCheckBottomSheet()
//                modal.show(parentFragmentManager, modal.tag)
//            }
        }
    }
}