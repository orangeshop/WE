package com.we.presentation.home

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.adapter.InvitationAdapter
import com.we.presentation.databinding.FragmentInvitationBinding
import com.we.presentation.home.model.InvitationUiState
import com.we.presentation.home.viewmodel.InvitationViewModel
import com.we.presentation.util.addCustomItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class InvitationFragment : BaseFragment<FragmentInvitationBinding>(R.layout.fragment_invitation) {
    private lateinit var invitationAdapter: InvitationAdapter

    private val invitationViewModel: InvitationViewModel by viewModels()

    override fun initView() {
        initInvitationAdapter()
        initClickEventListener()
        observeInvitationUiState()
    }


    private fun initInvitationAdapter() {
        invitationAdapter = InvitationAdapter()
        binding.vpInvitation.apply {
            adapter = invitationAdapter
            offscreenPageLimit = 1
            this.addCustomItemDecoration()
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.shareVisible = position != invitationAdapter.itemCount - 1
                }
            })
        }


    }

    private fun observeInvitationUiState() {
        invitationViewModel.invitationUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is InvitationUiState.InvitationSuccess -> {
                        invitationAdapter.submitList(it.data)
                    }

                    is InvitationUiState.InvitationError -> {

                    }

                    else -> {

                    }
                }

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initClickEventListener() {
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }
            invitationAdapter.setItemClickListener {
                val url = "https://j11d104.p.ssafy.io/be/v1/invitation/formal/$it"
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                })
            }
        }
    }
}