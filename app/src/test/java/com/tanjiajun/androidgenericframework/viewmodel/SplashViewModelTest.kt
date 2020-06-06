package com.tanjiajun.androidgenericframework.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.ui.main.viewmodel.SplashViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by TanJiaJun on 2020/5/26.
 */
@RunWith(JUnit4::class)
class SplashViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: UserInfoRepository

    private lateinit var viewModel: SplashViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = SplashViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun navigateToPage_success() {
        runBlocking {
            every { repository.isLogin() } returns true
            viewModel.navigateToPage()
            val observer = mockk<Observer<Boolean>>(relaxed = true)
            viewModel.isNavigateToMainActivity.observeForever(observer)
            viewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() }
            verify { observer.onChanged(match { it }) }
        }
    }

    @Test
    fun navigateToPage_failure() {
        runBlocking {
            every { repository.isLogin() } returns false
            viewModel.navigateToPage()
            val observer = mockk<Observer<Boolean>>(relaxed = true)
            viewModel.isNavigateToMainActivity.observeForever(observer)
            viewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() }
            verify { observer.onChanged(match { !it }) }
        }
    }

}