package com.we.presentation.mypage

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.we.model.BankData
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.HomeViewPagerAccountAdapter
import com.we.presentation.databinding.FragmentMyPageBinding
import com.we.presentation.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun initView() {
        setUpAccountViewPager()
    }

    private fun setUpAccountViewPager() {

        lifecycleScope.launch {
            myPageViewModel.accountList.collect { list ->
                val adapter = HomeViewPagerAccountAdapter(
                    list.toMutableList(),
                    accountClickListener = {
                        Toast.makeText(requireContext(), "허용하지 않음", Toast.LENGTH_SHORT).show()
                    },
                    accountRemittance = {
                        Toast.makeText(requireContext(), "허용하지 않음", Toast.LENGTH_SHORT).show()
                    },
                    true
                )

                binding.apply {
                    vpMyAccount.adapter = adapter
                    vpTotalAccountDotsIndicator.attachTo(vpMyAccount)
                }
            }
        }
    }
}