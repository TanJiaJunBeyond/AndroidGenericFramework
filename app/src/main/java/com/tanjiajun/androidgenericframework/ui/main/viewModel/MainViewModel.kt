package com.tanjiajun.androidgenericframework.ui.main.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.data.repository.RepositoryOfGitHubRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-08-24.
 */
class MainViewModel(
        val repository: RepositoryOfGitHubRepository
) : BaseViewModel() {

    private val languageNames: MutableList<String> by lazy {
        mutableListOf<String>().apply { addAll(repository.getDefaultLanguageNames()) }
    }

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    fun getDefaultLanguageNames(): List<String> =
            languageNames

    fun getLastLanguageNameIndex(): Int =
            languageNames.lastIndex

    fun getLastLanguageName(): String =
            languageNames[languageNames.lastIndex]

    fun addLanguageName() =
            _index.value?.let {
                languageNames.add(repository.getMoreLanguageNames()[it])
                it.inc()
            }

    interface Handlers {

        fun onPersonalCenterClick(view: View)

        fun onStateClick(view: View)

    }

}