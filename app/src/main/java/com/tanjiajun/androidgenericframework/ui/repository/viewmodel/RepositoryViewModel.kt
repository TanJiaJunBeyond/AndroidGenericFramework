package com.tanjiajun.androidgenericframework.ui.repository.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryData
import com.tanjiajun.androidgenericframework.data.repository.RepositoryOfGitHubRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2020-02-07.
 */
class RepositoryViewModel(
        val repository: RepositoryOfGitHubRepository
) : BaseViewModel() {

    private val _repositories = MutableLiveData<List<RepositoryData>>()
    val repositories: LiveData<List<RepositoryData>> = _repositories

    fun getRepositories(languageName: String) =
            launch(
                    isShowDialog = true,
                    block = { repository.getRepositories(languageName) },
                    success = { _repositories.value = it },
                    error = {},
                    complete = {}
            )

}