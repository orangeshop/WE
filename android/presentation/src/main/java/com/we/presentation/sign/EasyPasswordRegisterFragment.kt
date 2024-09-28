package com.we.presentation.sign

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentEasyPasswordRegisterBinding
import com.we.presentation.remittance.viewmodel.RemittanceViewModel
import com.we.presentation.sign.model.SignUpUiState
import com.we.presentation.sign.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executor

@AndroidEntryPoint
class EasyPasswordRegisterFragment :
    BaseFragment<FragmentEasyPasswordRegisterBinding>(R.layout.fragment_easy_password_register) {

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    private val remittanceViewModel : RemittanceViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private lateinit var buttonList: MutableList<Button>
    private lateinit var passwordList: MutableList<View>

    private val args : EasyPasswordRegisterFragmentArgs by navArgs()

    override fun initView() {
        Timber.d("EasyPasswordRegisterFragment initView ${args.easyPasswordType}")
        if(args.easyPasswordType == true) {

            initButtonList()
            initPasswordList()
            initClickEvent()
            observeEasyPassWord()
            observeEasyPasswordCheck()
            observeSignUpUiState()
        }
        else{
            initButtonList()
            initPasswordList()
            initClickEvent()
            observeEasyPassWord()
            initTransferSetting()
        }
    }

    private fun initTransferSetting(){
        binding.apply {
            tvRegisterEasyPassword.visibility = View.GONE
        }
    }

    private fun initButtonList() {
        buttonList = mutableListOf()
        val randomNumbers = (0..9).shuffled().take(10)

        for (i in 0 until binding.tlEasyPassword.childCount) {
            val tableRow = binding.tlEasyPassword.getChildAt(i) as TableRow
            for (j in 0 until tableRow.childCount) {
                if (i == 3 && (j == 0 || j == 2)) continue
                val button = tableRow.getChildAt(j) as Button
                buttonList.add(button)
            }
        }
        buttonList.forEachIndexed { index, button ->
            button.text = randomNumbers[index].toString()
        }
    }

    private fun initPasswordList() {
        passwordList = mutableListOf()
        val clPassword = binding.icPassword.clPassword

        clPassword.children.forEach {
            passwordList.add(it)
        }
    }


    private fun initClickEvent() {
        binding.apply {
            ivBack.setOnClickListener {
                signUpViewModel.clearEasyPasswordAndCheck()
                navigatePopBackStack()
            }
            buttonList.forEach { button ->
                button.setOnClickListener {
                    setBtnSelected(button)
                }
            }
            binding.btnTen.setOnClickListener {
                navigateDestination(R.id.action_fragment_easy_password_register_to_fragment_sign_up_success)
            }
            binding.btnTwelve.setOnClickListener {
                signUpViewModel.addRemoveEasyPassword(false)
            }
        }
    }

    private fun setBtnSelected(button: Button) {
        if (signUpViewModel.easyPasswordType.value) {
            signUpViewModel.addRemoveEasyPassword(true, button.text.toString())
        } else {
            signUpViewModel.addRemoveEasyPasswordCheck(true, button.text.toString())
        }
        val randomButtons = buttonList.shuffled().take(2).toMutableList()
        val pair = Pair(randomButtons[0], randomButtons[1])
        viewLifecycleOwner.lifecycleScope.launch {
            pair.first.isSelected = true
            pair.second.isSelected = true
            button.isSelected = true
            delay(200L)
            pair.first.isSelected = false
            pair.second.isSelected = false
            button.isSelected = false
        }
    }

    private fun observeEasyPassWord() {
        signUpViewModel.signUpParam.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (signUpViewModel.easyPasswordType.value) {
                    setUpEasyPassWord(it.easyPassword)
                } else {
                    setUpEasyPassWordCheck(it.easyPasswordCheck)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setUpEasyPassWord(list: List<String>) {
        val count = list.size - 1
        for (i in 0..5) {
            passwordList[i].isSelected = i <= count
        }
        if (list.size == 6) {
            if (args.easyPasswordType == true) {
                signUpViewModel.setEasyPasswordType(false)
            }
            else{
                // 비번 작성 후 송금 로직 작성

                // 일반 송금과 축의금 송금 분기 처리를 해야함
                // true인 경우 일반 송금

                var passwordListToString = ""

                list.forEach { password ->
                    passwordListToString += password
                }

                remittanceViewModel.postTransfer(true, passwordListToString){
                    navigateDestination(R.id.action_easyPasswordRegisterFragment_to_remittanceFinishFragment, bundle = bundleOf("remittanceCheck" to it))
                }

                // false인 경우 축의금 송금
//                remittanceViewModel.postTransfer(false)
            }
        }
    }



    private fun setUpEasyPassWordCheck(list: List<String>) {
        val count = list.size - 1
        for (i in 0..5) {
            passwordList[i].isSelected = i <= count
        }
        if (list.size == 6) {
            signUpViewModel.checkEasyPasswordEquals()
        }
    }

    private fun observeEasyPasswordCheck() {
        signUpViewModel.easyPasswordType.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                binding.checkType = it
                clearPasswordListSelected()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun clearPasswordListSelected() {
        for (i in 0..5) {
            passwordList[i].isSelected = false
        }
    }

    private fun clearPasswordList() {
        passwordList = mutableListOf()
    }

    private fun observeSignUpUiState() {
        signUpViewModel.signUpUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is SignUpUiState.SignUpEmpty -> {}
                    is SignUpUiState.SignUpLoading -> {}
                    is SignUpUiState.SignUpSuccess -> {
                        navigateDestination(R.id.action_fragment_easy_password_register_to_fragment_sign_up_success)
                    }

                    is SignUpUiState.EasyPasswordSuccess -> {
                        signUpViewModel.signUp()
                    }

                    is SignUpUiState.SignUpError -> {
                        Timber.d("회원가입 오류 ${it.error}")
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

}