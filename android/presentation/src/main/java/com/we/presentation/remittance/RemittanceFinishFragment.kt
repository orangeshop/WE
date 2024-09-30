package com.we.presentation.remittance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentRemittanceFinishBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemittanceFinishFragment : BaseFragment<FragmentRemittanceFinishBinding>(R.layout.fragment_remittance_finish) {

    private val args : RemittanceFinishFragmentArgs by navArgs()

    override fun initView() {
        initReuslt(args.remittanceCheck)
    }

    fun initReuslt(result: Boolean){
        binding.apply {
            when(result){
                true -> {remittanceFinish.text = "성공"}
                false -> {remittanceFinish.text = "실패"}
            }
        }
    }
}