package com.we.presentation.account

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.presentation.R
import com.we.presentation.account.util.BankList
import com.we.presentation.account.viewmodel.AccountViewModel
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account) {
    private val accountViewModel: AccountViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun initView() {

        accountViewModel.setChooseBank(BankList(0, ""))
        accountViewModel.setAccountNumber("")

        accountBottomSheetClickListener()
        initTransferClickListener()
        bankChooseComplete()
        accountInputComplete()
        btnActivateCheck()
        observeAccountAuthSuccess()
    }

    private fun btnActivateCheck() {
        binding.apply {
            combine(
                accountViewModel.accountNumber.flowWithLifecycle(viewLifecycleOwner.lifecycle),
                accountViewModel.chooseBank.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            ) { accountNumber, chooseBank ->
                Timber.d("accountNumber : $accountNumber chooseBank : ${chooseBank.bankName}")
                if (accountNumber.isNotBlank() && chooseBank.bankName.isNotBlank()) {
                    tvRegisterAccount.isEnabled = true
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun accountInputComplete() {
        binding.apply {
            etAccountNumber.addTextChangedListener {
                accountViewModel.setAccountNumber(it.toString())
            }
        }
    }

    private fun bankChooseComplete() {
        viewLifecycleOwner.lifecycleScope.launch {
            accountViewModel.chooseBank.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach {

                    if (it.bankName != "") {
                        binding.apply {
                            tvAccountBankComplete.text = it.bankName
                            tvInputAccount.visibility = View.VISIBLE
                            etAccountNumber.visibility = View.VISIBLE
                        }
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }

    private fun initTransferClickListener() {
        binding.apply {
            tvRegisterAccount.setOnClickListener {
                accountViewModel.accountAuth()

            }

            ivAccountBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }

    private fun observeAccountAuthSuccess() {
        accountViewModel.accountAuthSuccess.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it) {
                    navigateDestination(R.id.action_accountFragment_to_accountTransferFragment)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun accountBottomSheetClickListener() {
        binding.apply {
            flChooseBank.setOnClickListener {
                val modal = AccountModalBottomSheet(true)
                modal.show(parentFragmentManager, modal.tag)
            }
        }
    }
}