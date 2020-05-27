package com.tanjiajun.androidgenericframework.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.data.userAccessTokenData
import com.tanjiajun.androidgenericframework.data.userInfoData
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by TanJiaJun on 2020/5/27.
 */
@ObsoleteCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var repository: UserInfoRepository

    private lateinit var viewModel: LoginViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = LoginViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun checkLoginEnable_success() {
        viewModel.username.value = "1120571286@qq.com"
        viewModel.password.value = "password"
        viewModel.checkLoginEnable()
        assertEquals(true, viewModel.isLoginEnable.value)
    }

    @Test
    fun checkLoginEnable_failure() {
        viewModel.username.value = null
        viewModel.password.value = null
        viewModel.checkLoginEnable()
        assertEquals(false, viewModel.isLoginEnable.value)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Test
    fun login_success() {
        runBlocking {
            viewModel.username.value = "1120571286@qq.com"
            viewModel.password.value = "password"
            coEvery { repository.authorizations() } returns userAccessTokenData
            coEvery { repository.getUserInfo() } returns userInfoData
            viewModel.login()
            val observer = mockk<Observer<Boolean>>(relaxed = true)
            viewModel.isLoginSuccess.observeForever(observer)
            viewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() }
            verify { observer.onChanged(match { it }) }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Test
    fun login_failure() {
        runBlocking {
            viewModel.username.value = "1120571286@qq.com"
            viewModel.password.value = "password"
            coEvery { repository.authorizations() } returns userAccessTokenData
            coEvery { repository.getUserInfo() } throws Throwable(message = "UnknownError")
            viewModel.login()
            val observer = mockk<Observer<String>>(relaxed = true)
            viewModel.uiLiveEvent.showSnackbarEvent.observeForever(observer)
            viewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() }
            verify { observer.onChanged(match { it == "0:UnknownError" }) }
        }
    }

}