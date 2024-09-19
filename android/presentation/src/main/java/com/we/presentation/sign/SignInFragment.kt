package com.we.presentation.sign

import android.content.Intent
import androidx.fragment.app.viewModels
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentSignInBinding
import com.we.presentation.main.MainActivity
import com.we.presentation.sign.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    private val signInViewModel : SignUpViewModel by viewModels()
    override fun initView() {
        initClickEvent()
    }

    private fun initClickEvent() {
        binding.apply {
            tvSignUp.setOnClickListener {
                navigateDestination(R.id.action_fragment_sign_in_to_fragment_sign_up)
            }
            tvSignInLogin.setOnClickListener{
                signInViewModel.singIn()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }

}