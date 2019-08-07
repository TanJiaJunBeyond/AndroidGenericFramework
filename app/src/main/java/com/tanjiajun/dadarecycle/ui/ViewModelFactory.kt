package com.tanjiajun.dadarecycle.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tanjiajun.dadarecycle.data.repository.UserRepository
import com.tanjiajun.dadarecycle.ui.user.viewModel.LoginViewModel

/**
 * Created by TanJiaJun on 2019-08-07.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(userRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

}