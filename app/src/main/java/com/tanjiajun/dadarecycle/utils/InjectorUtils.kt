package com.tanjiajun.dadarecycle.utils

import com.tanjiajun.dadarecycle.data.network.UserNetwork
import com.tanjiajun.dadarecycle.data.repository.UserRepository
import com.tanjiajun.dadarecycle.ui.user.viewModel.UserViewModelFactory

/**
 * Created by TanJiaJun on 2019-07-31.
 */
object InjectorUtils {

    private fun getUserRepository() =
            UserRepository.getInstance(UserNetwork.instance)

    fun getUserViewModelFactory() =
            UserViewModelFactory(getUserRepository())

}