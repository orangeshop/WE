package com.we.presentation.test

import androidx.lifecycle.viewModelScope
import com.data.repository.SignRepository
import com.we.presentation.sign.viewmodel.SignUpViewModel
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.text.isNotEmpty

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SignUpViewModelTest {

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var signRepository: SignRepository


    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        signRepository = mockk()
        signUpViewModel = SignUpViewModel(signRepository)
    }
    @Test
    fun `이메일 등록`() = runTest{
        //given
        signUpViewModel.setEmail("123123")
        //when
        val data = signUpViewModel.signUpParam.value
        //then
        assertEquals(data.email, "123123")
    }


    @Test
    fun `이메일,패스워드,닉네임 다 있는 경우`() = runTest {

        //when
        signUpViewModel.setEmail("123")
        signUpViewModel.setEmailName("1424")
        signUpViewModel.setPassword("14242")
        signUpViewModel.setNickName("242424")


        //then
        assertEquals(signUpViewModel.nextButtonActivate.first(), true)
    }

    @Test
    fun `이메일이 빈 경우,패스워드,닉네임 다 있는 경우`() = runTest {
        //when
        signUpViewModel.setPassword("14242")
        signUpViewModel.setNickName("242424")

        //then
        assertEquals(signUpViewModel.nextButtonActivate.first(), false)
    }

    @Test
    fun `패스워드가 빈 경우,이메일,닉네임 다 있는 경우`() = runTest {
        //when
        signUpViewModel.setEmail("123")
        signUpViewModel.setEmailName("1424")
        signUpViewModel.setNickName("242424")


        //then
        assertEquals(signUpViewModel.nextButtonActivate.first(), false)
    }

    @Test
    fun `닉네임이 빈 경우,이메일,패스워드 다 있는 경우`() = runTest {
        //when
        signUpViewModel.setEmail("123")
        signUpViewModel.setEmailName("1424")
        signUpViewModel.setPassword("14242")

        //then
        assertEquals(signUpViewModel.nextButtonActivate.first(), false)
    }

    @Test
    fun `간편 비밀번호 입력시에 리스트에 하나씩 추가`() {

        //when
        signUpViewModel.addRemoveEasyPassword(true, "2")

        //then
//        val data = signUpViewModel.easyPassword.value
//        assertEquals(data, listOf("2"))
    }

    @Test
    fun `간편 비밀번호 하나씩 삭제`() {
        //given
        signUpViewModel.addRemoveEasyPassword(true, "2")
        signUpViewModel.addRemoveEasyPassword(true, "4")
        signUpViewModel.addRemoveEasyPassword(true, "6")
        //when
        signUpViewModel.addRemoveEasyPassword(false)
        //then
//        val data = signUpViewModel.easyPassword.value
//        assertEquals(data, listOf("2", "4"))
    }


}