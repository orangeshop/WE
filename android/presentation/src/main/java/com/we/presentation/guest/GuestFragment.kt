package com.we.presentation.guest

import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.model.BankData
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.ShareData
import com.we.presentation.component.adapter.HomeViewPagerAccountAdapter
import com.we.presentation.databinding.FragmentGuestBinding
import com.we.presentation.guest.viewmodel.GuestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GuestFragment : BaseFragment<FragmentGuestBinding>(R.layout.fragment_guest) {
    private val guestViewModel: GuestViewModel by viewModels()
    private lateinit var homeAdapter: HomeViewPagerAccountAdapter

    override fun initView() {
        initClickEventListener()
        initTransfer()
        setUpAccountViewPager()
    }

    private fun initTransfer(){
        if(ShareData.transferType){
            navigateDestination(R.id.action_guestFragment_to_transfer_nav_graph)
        }
    }

    private fun initClickEventListener() {
        binding.apply {
            icCoupleRegister.flContent.setOnClickListener {
                navigateDestination(R.id.action_guestFragment_to_coupleFragment)
            }
        }
    }

    private fun setUpAccountViewPager() {
        homeAdapter = HomeViewPagerAccountAdapter(
            accountClickListener = { idx,account ->
                if (idx == homeAdapter.currentList.lastIndex) {
                    navigateDestination(R.id.action_guestFragment_to_accountFragment,
                        bundleOf("inputType" to false)
                    )
                    ShareData.guestTransfer = true
                } else {
                    navigateDestination(R.id.action_guestFragment_to_accountCheckFragment)
                }
            },
            accountRemittance = {},
            typeCheck = false,
            moreVertClickListener = { resultView, account, bankName ->
                val popupMenu = PopupMenu(requireContext(), resultView)
                popupMenu.menuInflater.inflate(R.menu.menu_guest_account_register, popupMenu.menu)

                // 메뉴 아이템 클릭 리스너 설정
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.representative -> {
                            // 대표 계좌 등록 동작
                            guestViewModel.postPriorAccount(account)
                            true
                        }

                        else -> false
                    }
                }
                // 팝업 메뉴 표시
                popupMenu.show()
            },
        )

        binding.apply {
            vpHomeBanner.adapter = homeAdapter
            vpHomeTotalBannerDotsIndicator.attachTo(vpHomeBanner)

            viewLifecycleOwner.lifecycleScope.launch {
                delay(700)
                vpHomeBanner.setCurrentItem(0, false)
            }

        }


        guestViewModel.accountList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                val updatedList = it.toMutableList().apply {
                    add(BankData())
                }
                homeAdapter.submitList(updatedList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }
}