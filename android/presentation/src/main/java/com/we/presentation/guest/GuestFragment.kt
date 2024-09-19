package com.we.presentation.guest

import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentGuestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuestFragment : BaseFragment<FragmentGuestBinding>(R.layout.fragment_guest) {

    override fun initView() {
        initClickEventListener()
    }

    private fun initClickEventListener(){
        binding.apply {
            icCoupleRegister.flContent.setOnClickListener{
                navigateDestination(R.id.action_guestFragment_to_coupleFragment)
            }
        }
    }
}