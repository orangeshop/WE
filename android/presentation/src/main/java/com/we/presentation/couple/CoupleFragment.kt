package com.we.presentation.couple

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.couple.viewmodel.CoupleViewModel
import com.we.presentation.databinding.FragmentCoupleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoupleFragment : BaseFragment<FragmentCoupleBinding>(R.layout.fragment_couple) {

    private val coupleViewModel: CoupleViewModel by viewModels()

    override fun initView() {
        initClickEventListener()
        initCoupleCode()
    }

    private fun initCoupleCode() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                coupleViewModel.coupleCode.collect { coupleData ->

                    binding.tvCoupleCode.text = coupleData.code
                }
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun initClickEventListener() {
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }

            tvInputComplete.setOnClickListener {
                coupleViewModel.setCoupleSuccessCode(etInputCouple.text.toString())
                coupleViewModel.postCouple(){
                    when(it){
                        true -> {
                            Toast.makeText(requireContext(), "매칭 성공", Toast.LENGTH_SHORT).show()
                        }
                        false ->{
                            Toast.makeText(requireContext(), "매칭 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}