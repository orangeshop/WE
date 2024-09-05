package com.we.presentation.sign

import android.content.Intent
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.we.presentation.databinding.FragmentSignUpSuccessBinding
import com.we.presentation.main.MainActivity

@AndroidEntryPoint
class SignUpSuccessFragment : BaseFragment<FragmentSignUpSuccessBinding>(R.layout.fragment_sign_up_success) {

    override fun initView() {
        initClickEvent()
    }

    private fun initClickEvent(){
        binding.apply {
            tvSignSuccessComplete.setOnClickListener{
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}