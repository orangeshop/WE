package com.we.presentation.sign

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentSignUpBinding
import com.we.presentation.sign.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun initView() {
        initClickEvent()
        observeNextButton()
        observeParam()
    }

    private fun observeParam() {
        binding.apply {
            etSignUpEmail.addTextChangedListener {
                signUpViewModel.setEmail(it.toString())
            }
            etSignUpEmailName.addTextChangedListener {
                signUpViewModel.setEmailName(it.toString())
            }
            etSignUpPassword.addTextChangedListener {
                signUpViewModel.setPassword(it.toString())
            }
            etSignUpNickname.addTextChangedListener {
                signUpViewModel.setNickName(it.toString())
            }
        }

    }


    private fun observeNextButton() { //회원가입 버튼 활성화
        signUpViewModel.nextButtonActivate.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                binding.tvSignUpComplete.run {
                    isSelected = it
                    isEnabled = it
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

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