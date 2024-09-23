package com.we.presentation.sign

import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentSignUpSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpSuccessFragment :
    BaseFragment<FragmentSignUpSuccessBinding>(R.layout.fragment_sign_up_success) {

    override fun initView() {
        initClickEvent()

    }

    private fun initClickEvent() {
        binding.apply {
            tvSignSuccessComplete.setOnClickListener {
                navigatePopBackStack()
            }
        }
    }
}