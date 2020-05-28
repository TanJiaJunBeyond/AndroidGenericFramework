package com.tanjiajun.androidgenericframework.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tanjiajun.androidgenericframework.data.repository.GitHubRepository
import com.tanjiajun.androidgenericframework.ui.main.viewmodel.MainViewModel
import com.tanjiajun.androidgenericframework.utils.Language
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by TanJiaJun on 2020/5/26.
 */
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: GitHubRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { repository.getDefaultLanguageNames() } returns listOf(
                Language.KOTLIN.languageName,
                Language.JAVA.languageName,
                Language.SWIFT.languageName,
                Language.JAVA_SCRIPT.languageName,
                Language.PYTHON.languageName,
                Language.GO.languageName,
                Language.CSS.languageName
        )
        every { repository.getMoreLanguageNames() } returns listOf(
                Language.PHP.languageName,
                Language.RUBY.languageName,
                Language.C_PLUS_PLUS.languageName,
                Language.C.languageName,
                Language.OTHER.languageName
        )
        viewModel = MainViewModel(repository)
    }

    @Test
    fun getDefaultLanguageNames_success() {
        assertEquals(Language.KOTLIN.languageName, viewModel.getDefaultLanguageNames()[0])
    }

    @Test
    fun getDefaultLanguageNames_failure() {
        assertNotEquals(Language.JAVA.languageName, viewModel.getDefaultLanguageNames()[0])
    }

    @Test
    fun getDefaultLanguageNamesCount_success() {
        assertEquals(7, viewModel.getDefaultLanguageNamesCount())
    }

    @Test
    fun getDefaultLanguageNamesCount_failure() {
        assertNotEquals(0, viewModel.getDefaultLanguageNamesCount())
    }

    @Test
    fun getLastLanguageNameIndex_success() {
        assertEquals(6, viewModel.getLastLanguageNameIndex())
    }

    @Test
    fun getLastLanguageNameIndex_failure() {
        assertNotEquals(0, viewModel.getLastLanguageNameIndex())
    }

    @Test
    fun addLanguageName_showAdd() {
        viewModel.languageNames.value?.addAll(listOf(
                Language.PHP.languageName,
                Language.RUBY.languageName,
                Language.C_PLUS_PLUS.languageName
        ))
        viewModel.index = 3
        viewModel.addLanguageName()
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isShowAdd.observeForever(observer)
        verify { observer.onChanged(match { it }) }
    }

    @Test
    fun addLanguageName_doNotShowAdd() {
        viewModel.languageNames.value?.addAll(listOf(
                Language.PHP.languageName,
                Language.RUBY.languageName,
                Language.C_PLUS_PLUS.languageName,
                Language.C.languageName
        ))
        viewModel.index = 4
        viewModel.addLanguageName()
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isShowAdd.observeForever(observer)
        verify { observer.onChanged(match { !it }) }
    }

}