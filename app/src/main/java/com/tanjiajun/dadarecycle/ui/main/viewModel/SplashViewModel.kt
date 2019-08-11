package com.tanjiajun.dadarecycle.ui.main.viewModel

import com.tanjiajun.dadarecycle.data.repository.UserRepository
import com.tanjiajun.dadarecycle.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-08-12.
 */
class SplashViewModel(
        private val repository: UserRepository
) : BaseViewModel() {

    fun isLogin(): Boolean = repository.isUserInfoCached()

}