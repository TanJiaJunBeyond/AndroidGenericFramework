package com.tanjiajun.androidgenericframework.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.tanjiajun.androidgenericframework.data.repository.GitHubRepository
import com.tanjiajun.androidgenericframework.data.repositoryData
import com.tanjiajun.androidgenericframework.ui.repository.viewmodel.RepositoryViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by TanJiaJun on 2020/5/28.
 */
@RunWith(JUnit4::class)
class RepositoryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: GitHubRepository

    private lateinit var viewModel: RepositoryViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = RepositoryViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getRepositories_success() {
        runBlocking {
            val languageName = "Kotlin"
            coEvery { repository.getRepositories(languageName) } returns listOf(repositoryData)
            viewModel.getRepositories(languageName)
            val observer = mockk<Observer<Boolean>>(relaxed = true)
            viewModel.isShowRepositoryView.observeForever(observer)
            viewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() }
            verify { observer.onChanged(match { it }) }
        }
    }

    @Test
    fun getRepositories_failure() {
        runBlocking {
            val languageName = "Kotlin"
            coEvery { repository.getRepositories(languageName) } throws Throwable("UnknownError")
            viewModel.getRepositories(languageName)
            val observer = mockk<Observer<Boolean>>(relaxed = true)
            viewModel.isShowErrorView.observeForever(observer)
            viewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() }
            verify { observer.onChanged(match { it }) }
        }
    }

}