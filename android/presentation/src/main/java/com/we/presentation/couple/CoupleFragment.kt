package com.we.presentation.couple

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.couple.viewmodel.CoupleViewModel
import com.we.presentation.databinding.FragmentCoupleBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CoupleFragment : BaseFragment<FragmentCoupleBinding>(R.layout.fragment_couple) {

    private val viewModel by viewModels<CoupleViewModel>()

    override fun initView() {
        initClickEventListener()
        viewModel.getCoupleCode()
    }

    private fun initClickEventListener(){
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}