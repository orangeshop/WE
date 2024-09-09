package com.we.presentation.sign

import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {
    override fun initView() {
        initClickEvent()
    }

    private fun initClickEvent() {
        binding.apply {
            icTitle.ivBack.setOnClickListener {
                navigatePopBackStack()
            }
            tvSignUpComplete.setOnClickListener {
                navigateDestination(R.id.action_fragment_sign_up_to_fragment_easy_password_register)
            }
        }
    }
}