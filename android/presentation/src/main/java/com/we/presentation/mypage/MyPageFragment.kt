package com.we.presentation.mypage

import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.HomeViewPagerAccountAdapter
import com.we.presentation.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun initView() {
        setUpAccountViewPager()
    }

    private fun setUpAccountViewPager() {
        val test = arrayListOf("1", "2", "3")

        val adapter = HomeViewPagerAccountAdapter(test, accountClickListener = {
            navigateDestination(R.id.action_homeFragment_accountCheckFragment)
        })
        binding.apply {
            vpMyAccount.adapter = adapter
            vpTotalAccountDotsIndicator.attachTo(vpMyAccount)
        }
    }
}