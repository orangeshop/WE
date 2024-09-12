package com.we.presentation.sign

import android.widget.Button
import android.widget.TableRow
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.we.presentation.R
import com.we.presentation.base.BaseFragment
import com.we.presentation.databinding.FragmentEasyPasswordRegisterBinding
import com.we.presentation.sign.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EasyPasswordRegisterFragment :
    BaseFragment<FragmentEasyPasswordRegisterBinding>(R.layout.fragment_easy_password_register) {

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    private lateinit var buttonList: MutableList<Button>

    override fun initView() {
        initPassword()
        initClickEvent()
    }


    private fun initPassword() {
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

    private fun initClickEvent() {
        binding.apply {
            ivBack.setOnClickListener {
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
        }
    }

    private fun setBtnSelected(button: Button) {
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
}