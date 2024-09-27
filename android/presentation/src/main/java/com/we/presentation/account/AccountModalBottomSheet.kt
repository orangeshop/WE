package com.we.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.we.presentation.R
import com.we.presentation.account.viewmodel.AccountViewModel
import com.we.presentation.base.BaseBottomSheet
import com.we.presentation.component.adapter.AccountBankChooseAdapter
import com.we.presentation.databinding.DialogChooseBankBinding
import com.we.presentation.remittance.viewmodel.RemittanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AccountModalBottomSheet : BaseBottomSheet<DialogChooseBankBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogChooseBankBinding
        get() = DialogChooseBankBinding::inflate

    private val accountViewModel: AccountViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val remittanceViewModel: RemittanceViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun setupViews() {
        val adapter = AccountBankChooseAdapter { item ->
            accountViewModel.setChooseBank(item)
            remittanceViewModel.setChooseBank(item)

            viewLifecycleOwner.lifecycleScope.launch {
                accountViewModel.chooseBank.collect { value ->
                    if (value.bankName.isNotEmpty()) {
                        Timber.d("item ${value.bankName} ${value.bankIcList}")
                        dismiss()
                    }
                }
            }
        }

        binding.apply {
            rvChooseBank.adapter = adapter
            lifecycleScope.launch {
                accountViewModel.bankList.collect { list ->
                    adapter.submitList(list)
                }
            }
        }
    }
}
