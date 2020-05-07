package com.tanjiajun.androidgenericframework.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tanjiajun.androidgenericframework.data.repository.GitHubRepository
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.ui.main.viewModel.MainViewModel
import com.tanjiajun.androidgenericframework.ui.main.viewModel.SplashViewModel
import com.tanjiajun.androidgenericframework.ui.repository.viewmodel.RepositoryViewModel
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.LoginViewModel
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.PersonalCenterViewModel

/**
 * Created by TanJiaJun on 2019-08-07.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
        private val userRepository: UserInfoRepository,
        private val repositoryOfGitHubRepository: GitHubRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(LoginViewModel::class.java) ->
                        LoginViewModel(userRepository)

                    isAssignableFrom(SplashViewModel::class.java) ->
                        SplashViewModel(userRepository)

                    isAssignableFrom(MainViewModel::class.java) ->
                        MainViewModel(repositoryOfGitHubRepository)

                    isAssignableFrom(PersonalCenterViewModel::class.java) ->
                        PersonalCenterViewModel(userRepository)

                    isAssignableFrom(RepositoryViewModel::class.java) ->
                        RepositoryViewModel(repositoryOfGitHubRepository)

                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

}