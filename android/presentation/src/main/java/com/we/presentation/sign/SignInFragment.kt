package com.we.presentation.sign

import android.content.Intent
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentSignInBinding
import com.we.presentation.main.MainActivity
import com.we.presentation.sign.model.SignInUiState
import com.we.presentation.sign.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    private val signInViewModel: SignInViewModel by viewModels()
    override fun initView() {
        initClickEvent()
        observeSignInParam()
        observeSignInUiStatus()
    }

    private fun initClickEvent() {
        binding.apply {
            tvSignUp.setOnClickListener {
                navigateDestination(R.id.action_fragment_sign_in_to_fragment_sign_up)
            }

            tvSignInLogin.setOnClickListener {
                signInViewModel.singIn()
            }
        }
    }

    private fun observeSignInParam() {
        binding.apply {
            etSignInEmail.addTextChangedListener {
                signInViewModel.setSignInParam(EMAIL, it.toString())
            }
            etSignInPassword.addTextChangedListener {
                signInViewModel.setSignInParam(PASSWORD, it.toString())
            }
        }
    }

    private fun observeSignInUiStatus() {
        signInViewModel.signInUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is SignInUiState.SignInSuccess -> {
                        Timber.tag("로그인 메인").d("${it.coupleJoined}")
                        startActivity(Intent(requireActivity(), MainActivity::class.java).apply {
                            putExtra("type", it.coupleJoined)
                        })
                        requireActivity().finish()
                    }

                    is SignInUiState.SignInError -> {

                    }

                    else -> {

                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        const val EMAIL = true
        const val PASSWORD = false
    }

}