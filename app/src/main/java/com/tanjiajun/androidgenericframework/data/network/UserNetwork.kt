package com.tanjiajun.androidgenericframework.data.network

import com.tanjiajun.androidgenericframework.data.model.user.request.LoginRequestData
import com.tanjiajun.androidgenericframework.data.network.retrofit.UserRetrofit

/**
 * Created by TanJiaJun on 2019-08-01.
 */
class UserNetwork private constructor() : BaseNetwork() {

    private val userService = UserRetrofit.instance.userService

    suspend fun authorizations() =
            userService.authorizations(LoginRequestData.generate())

    suspend fun fetchUserInfo() =
            userService.fetchUserInfo()

    companion object {
        val instance by lazy { UserNetwork() }
    }

}