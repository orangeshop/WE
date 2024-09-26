package com.we.presentation.home

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.model.BankData
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.HomeViewPagerAccountAdapter
import com.we.presentation.component.adapter.HomeViewPagerBannerAdapter
import com.we.presentation.databinding.FragmentHomeBinding
import com.we.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "HomeFragment_싸피"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun initView() {
        homeViewModel.getAccountList()
        setUpAccountViewPager()
        setUpBannerViewPager()
        initClickEventListener()
    }


    private fun menuSetting() {
        object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_account_register, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }
    }


    private fun setUpAccountViewPager() {
        lateinit var adapter: HomeViewPagerAccountAdapter

        homeViewModel.accountList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { list ->
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
                    typeCheck = false,
                    moreVertClickListener = {

                    }
                )
                binding.apply {
                    vpHomeAccount.adapter = adapter
                    vpHomeTotalAccountDotsIndicator.attachTo(vpHomeAccount)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

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