package com.we.presentation.accountcheck

import android.view.LayoutInflater
import android.view.ViewGroup
import com.we.presentation.base.BaseBottomSheet
import com.we.presentation.databinding.DialogAccountCheckBinding

class AccountCheckBottomSheet : BaseBottomSheet<DialogAccountCheckBinding>(){
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogAccountCheckBinding {
        return DialogAccountCheckBinding.inflate(inflater, container, false)
    }


    override fun setupViews() {

    }
}