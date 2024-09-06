package com.we.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.we.presentation.R
import com.we.presentation.component.adapter.AccountBankChooseAdapter
import com.we.presentation.databinding.DialogChooseBankBinding

class AccountModalBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding : DialogChooseBankBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogChooseBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val test = arrayListOf("1","2","3", "1","2","3", "1","2","3", "1","2","3", "1","2","3", "1","2","3", "1","2","3", "1","2","3", "1","2","3")
        val adapter = AccountBankChooseAdapter()
        binding.apply {
            rvChooseBank.adapter = adapter
            adapter.submitList(test)

        }
    }
}