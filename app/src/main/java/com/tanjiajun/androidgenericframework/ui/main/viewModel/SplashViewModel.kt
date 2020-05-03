package com.tanjiajun.androidgenericframework.ui.main.viewModel

import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel
import javax.inject.Inject

/**
 * Created by TanJiaJun on 2019-08-12.
 */
class SplashViewModel @Inject constructor(
        private val repository: UserInfoRepository
) : BaseViewModel() {

    fun isLogin(): Boolean =
            repository.isLogin()

}