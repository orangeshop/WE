package com.we.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.we.presentation.R
import com.we.presentation.account.viewmodel.AccountViewModel
import com.we.presentation.component.adapter.AccountBankChooseAdapter
import com.we.presentation.databinding.DialogChooseBankBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AccountModalBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: DialogChooseBankBinding
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogChooseBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AccountBankChooseAdapter() { item ->

            accountViewModel.setChooseBank(item)

            lifecycleScope.launch {
                accountViewModel.chooseBank.collect {value ->
                    if(value.bankName != ""){
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