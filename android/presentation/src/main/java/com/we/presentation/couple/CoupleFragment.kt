package com.we.presentation.couple

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.couple.viewmodel.CoupleViewModel
import com.we.presentation.databinding.FragmentCoupleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CoupleFragment : BaseFragment<FragmentCoupleBinding>(R.layout.fragment_couple) {

    private val coupleViewModel : CoupleViewModel by viewModels()

    override fun initView() {
        initClickEventListener()
        coupleViewModel.getCoupleCode(){
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                    coupleViewModel.coupleCode.collect{ coupleData ->
                        Timber.d("Succese "+coupleData)
                        binding.tvCoupleCode.text = coupleData.code
                    }
                }
            }
        }
    }

    private fun initClickEventListener(){
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}