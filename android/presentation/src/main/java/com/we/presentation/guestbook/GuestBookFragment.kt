package com.we.presentation.guestbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentGuestBookBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuestBookFragment : BaseFragment<FragmentGuestBookBinding>(R.layout.fragment_guest_book) {
    override fun initView() {
        initClickEventListener()
    }

    private fun initClickEventListener() {
        binding.apply {
            ivQrCodeBack.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}