package com.we.presentation.home

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.core.os.bundleOf
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val TAG = "HomeFragment_싸피"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var homeAdapter: HomeViewPagerAccountAdapter

    override fun initView() {
        setUpAccountViewPager()
        setUpBannerViewPager()
        initClickEventListener()

    }

    private fun setUpAccountViewPager() {


        homeAdapter = HomeViewPagerAccountAdapter(
            accountClickListener = { idx ->
                if (idx == homeAdapter.currentList.lastIndex) {
                    navigateDestination(R.id.action_homeFragment_to_accountFragment)
                } else {
                    navigateDestination(R.id.action_homeFragment_accountCheckFragment)
                }
            },
            accountRemittance = { account ->
                navigateDestination(
                    R.id.action_homeFragment_to_remittance_gragh,
                    bundle = bundleOf("account" to account)
                )
            },
            typeCheck = false,
            moreVertClickListener = { resultView, account, bankName ->
                Timber.d("asdasd")
                val popupMenu = PopupMenu(requireContext(), resultView)
                popupMenu.menuInflater.inflate(R.menu.menu_account_register, popupMenu.menu)

                // 메뉴 아이템 클릭 리스너 설정
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.representative -> {
                            // 대표 계좌 등록 동작
                            homeViewModel.postPriorAccount(account)
                            true
                        }

                        R.id.couple -> {
                            // 커플 계좌 등록 동작
                            homeViewModel.postCoupleAccount(account, bankName)
                            true
                        }

                        else -> false
                    }
                }
                // 팝업 메뉴 표시
                popupMenu.show()
            }
        )

        binding.apply {
            vpHomeAccount.adapter = homeAdapter
            vpHomeTotalAccountDotsIndicator.attachTo(vpHomeAccount)

            viewLifecycleOwner.lifecycleScope.launch {
                delay(700)
                vpHomeAccount.setCurrentItem(0, false)
            }
        }


        homeViewModel.accountList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { list ->
                Timber.d("list flowWithLifecycle: $list")
                homeAdapter.submitList(list)
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

    override fun onResume() {
        super.onResume()
        homeViewModel.getAccountList()
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