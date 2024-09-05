package com.we.presentation.sign

import android.content.Intent
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentSignInBinding
import com.we.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    override fun initView() {
        initClickEvent()
    }

    private fun initClickEvent() {
        binding.apply {
            tvSignUp.setOnClickListener {
                navigateDestination(R.id.action_fragment_sign_in_to_fragment_sign_up)
            }
            tvSignInLogin.setOnClickListener{
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }

}