package com.we.presentation.home

import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.HomeViewPagerAccountAdapter
import com.we.presentation.component.adapter.HomeViewPagerBannerAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.we.presentation.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initView() {
        setUpAccountViewPager()
        setUpBannerViewPager()
    }


    private fun setUpAccountViewPager(){
        val test = arrayListOf("1","2","3")

        val adapter = HomeViewPagerAccountAdapter(test)
        binding.vpHomeAccount.adapter = adapter
        binding.vpHomeTotalAccountDotsIndicator.attachTo(binding.vpHomeAccount)
    }

    private fun setUpBannerViewPager(){
        val test = arrayListOf("1","2","3")

        val adapter = HomeViewPagerBannerAdapter(test)
        binding.vpHomeBanner.adapter = adapter
        binding.vpHomeTotalBannerDotsIndicator.attachTo(binding.vpHomeBanner)
    }

}