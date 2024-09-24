package com.we.presentation.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.we.model.BankData
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.HomeViewPagerAccountAdapter
import com.we.presentation.component.adapter.HomeViewPagerBannerAdapter
import com.we.presentation.databinding.FragmentHomeBinding
import com.we.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment_싸피"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun initView() {
        setUpAccountViewPager()
        setUpBannerViewPager()
        initClickEventListener()
    }


    private fun setUpAccountViewPager() {
        lateinit var adapter : HomeViewPagerAccountAdapter

        lifecycleScope.launch {
            homeViewModel.accountList.collect { list ->
                adapter = HomeViewPagerAccountAdapter(
                    list,
                    accountClickListener = { idx ->

                        if (idx == list.lastIndex) {
                            navigateDestination(R.id.action_homeFragment_to_accountFragment)
                        } else {
                            navigateDestination(R.id.action_homeFragment_accountCheckFragment)
                        }
                    },
                    accountRemittance = {
                        navigateDestination(R.id.action_fragment_home_to_remittanceFragment)
                    },
                    typeCheck = false
                )
            }
        }


        binding.apply {
            vpHomeAccount.adapter = adapter
            vpHomeTotalAccountDotsIndicator.attachTo(vpHomeAccount)
        }
    }

    private fun setUpBannerViewPager() {
        val test = arrayListOf("1", "2", "3")

        val adapter = HomeViewPagerBannerAdapter(test)
        binding.apply {
            vpHomeBanner.adapter = adapter
            vpHomeTotalBannerDotsIndicator.attachTo(vpHomeBanner)
        }
    }


    private fun initClickEventListener() {
        binding.apply {
            icInvitation.flContent.setOnClickListener {
                navigateDestination(R.id.action_homeFragment_to_invitationFragment)
            }

            icQrCode.flContent.setOnClickListener {
                navigateDestination(R.id.action_homeFragment_to_guestBookFragment)
            }


        }
    }

}