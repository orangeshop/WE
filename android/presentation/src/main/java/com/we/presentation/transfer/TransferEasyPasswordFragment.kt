package com.we.presentation.transfer

import android.view.View
import android.widget.Button
import android.widget.TableRow
import androidx.biometric.BiometricPrompt
import androidx.core.view.children
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.component.ShareData
import com.we.presentation.databinding.FragmentTransferEasyPasswordBinding
import com.we.presentation.transfer.viewmodel.TransferViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executor


@AndroidEntryPoint
class TransferEasyPasswordFragment :
    BaseFragment<FragmentTransferEasyPasswordBinding>(R.layout.fragment_transfer_easy_password) {

    private val transferViewModel: TransferViewModel by hiltNavGraphViewModels(R.id.transfer_nav_graph)

    private lateinit var buttonList: MutableList<Button>
    private lateinit var passwordList: MutableList<View>

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun initView() {
        initButtonList()
        initPasswordList()
        initClickEvent()
        observeEasyPassword()
        observeTransferSuccess()
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
                transferViewModel.clearEasyPasswordAndCheck()
                navigatePopBackStack()
            }
            buttonList.forEach { button ->
                button.setOnClickListener {
                    setBtnSelected(button)
                }
            }
            binding.btnTen.setOnClickListener {

            }
            binding.btnTwelve.setOnClickListener {
                transferViewModel.addRemoveEasyPassword(false)
            }
        }
    }

    private fun setBtnSelected(button: Button) {
        transferViewModel.addRemoveEasyPassword(true, button.text.toString())
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

    private fun observeEasyPassword() {
        transferViewModel.easyPassword.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                setUpEasyPassWord(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setUpEasyPassWord(list: List<String>) {
        val count = list.size - 1
        for (i in 0..5) {
            passwordList[i].isSelected = i <= count
        }
        if (list.size == 6) {
            transferViewModel.postTransfer(ShareData.legId)
        }
    }

    private fun observeTransferSuccess() {
        transferViewModel.transferSuccess.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it) {
                    Timber.tag("이체").d("성공")
                    navigateDestination(R.id.action_transferEasyPasswordFragment_to_transferSuccessFragment)
                } else {
                    Timber.tag("이체").d("실패")
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

}