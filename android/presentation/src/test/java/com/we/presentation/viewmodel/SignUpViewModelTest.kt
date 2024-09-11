package com.we.presentation.viewmodel

import com.data.repository.SignRepository
import com.we.presentation.sign.model.SignUpUiState
import com.we.presentation.sign.viewmodel.SignUpViewModel
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SignUpViewModelTest {

    private lateinit var signUpViewModel : SignUpViewModel
    private lateinit var signRepository: SignRepository
    private lateinit var signUpState : SignUpUiState.SignParam

    @Before
    fun setUp(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        signRepository = mockk()
        signUpViewModel = SignUpViewModel(signRepository)
    }

    @Test
    fun `이메일,패스워드,닉네임 다 있는 경우`(){

        //given
        val signUpState = MutableStateFlow(SignUpUiState.SignParam())

        //when

        signUpState.value = signUpState.value.copy(email = "야호")
        signUpState.value = signUpState.value.copy(nickname = "요호")
        signUpState.value = signUpState.value.copy(password = "1234")

        //then
        assertEquals(signUpState.value.signUpValid, true)
    }

    @Test
    fun `이메일이 빈 경우,패스워드,닉네임 다 있는 경우`(){

        //given
        val signUpState = MutableStateFlow(SignUpUiState.SignParam())

        //when
        signUpState.value = signUpState.value.copy(nickname = "요호")
        signUpState.value = signUpState.value.copy(password = "1234")

        //then
        assertEquals(signUpState.value.signUpValid, false)
    }

    @Test
    fun `패스워드가 빈 경우,이메일,닉네임 다 있는 경우`(){

        //given
        val signUpState = MutableStateFlow(SignUpUiState.SignParam())

        //when
        signUpState.value = signUpState.value.copy(email = "야호")
        signUpState.value = signUpState.value.copy(nickname = "요호")

        //then
        assertEquals(signUpState.value.signUpValid, false)
    }

    @Test
    fun `닉네임이 빈 경우,이메일,패스워드 다 있는 경우`(){

        //given
        val signUpState = MutableStateFlow(SignUpUiState.SignParam())

        //when
        signUpState.value = signUpState.value.copy(email = "야호")
        signUpState.value = signUpState.value.copy(password = "1234")

        //then
        assertEquals(signUpState.value.signUpValid, false)
    }

}