package com.tanjiajun.dadarecycle.ui.user.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tanjiajun.dadarecycle.data.repository.UserRepository

/**
 * Created by TanJiaJun on 2019-08-04.
 */
@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
        private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            LoginViewModel(userRepository) as T

}