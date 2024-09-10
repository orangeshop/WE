package com.we.presentation.home

import androidx.viewpager2.widget.ViewPager2
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.InvitationAdapter
import com.we.presentation.databinding.FragmentInvitationBinding
import com.we.presentation.util.addCustomItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvitationFragment : BaseFragment<FragmentInvitationBinding>(R.layout.fragment_invitation) {
    private lateinit var invitationAdapter: InvitationAdapter

    override fun initView() {
        initInvitationAdapter()
        initClickEventListener()
    }

    private fun initInvitationAdapter() {
        val testList = listOf("1", "1", "1", "1")
        invitationAdapter = InvitationAdapter()
        binding.vpInvitation.apply {
            adapter = invitationAdapter
            offscreenPageLimit = 1
            this.addCustomItemDecoration()
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.shareVisible = position != testList.size - 1
                }
            })
        }

        invitationAdapter.submitList(listOf("1", "1", "1", "1"))
    }

    private fun initClickEventListener() {
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}