package com.tanjiajun.androidgenericframework.ui.repository.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryData
import com.tanjiajun.androidgenericframework.data.network.repository.RepositoryNetwork
import com.tanjiajun.androidgenericframework.ui.BaseViewModel
import java.time.LocalDateTime

/**
 * Created by TanJiaJun on 2020-02-07.
 */
class RepositoryViewModel(
        val network: RepositoryNetwork
) : BaseViewModel() {

    private val _repositories = MutableLiveData<List<RepositoryData>>()
    val repositories: LiveData<List<RepositoryData>> = _repositories

    fun getRepositories(language: String) =
            launch(
                    isShowDialog = true,
                    block = {
                        network.fetchRepositories(
                                language = language,
                                fromDateTime = LocalDateTime.now().minusMonths(1)
                        )
                    },
                    success = { _repositories.value = it },
                    error = {},
                    complete = {}
            )

}