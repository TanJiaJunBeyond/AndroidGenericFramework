package com.tanjiajun.androidgenericframework.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.data.userInfoData
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.PersonalCenterViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by TanJiaJun on 2020/5/28.
 */
@RunWith(JUnit4::class)
class PersonalCenterViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: UserInfoRepository

    private lateinit var viewModel: PersonalCenterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { repository.getAvatarUrl() } returns userInfoData.avatarUrl
        every { repository.getName() } returns userInfoData.login
        viewModel = PersonalCenterViewModel(repository)
    }

    @Test
    fun showTitle_success() {
        viewModel.showTitle("个人中心")
        val observer = mockk<Observer<String>>(relaxed = true)
        viewModel.title.observeForever(observer)
        verify { observer.onChanged(match { it == "个人中心" }) }
    }

    @Test
    fun showTitle_failure() {
        viewModel.showTitle("标题")
        val observer = mockk<Observer<String>>(relaxed = true)
        viewModel.title.observeForever(observer)
        verify { observer.onChanged(match { it != "个人中心" }) }
    }

}