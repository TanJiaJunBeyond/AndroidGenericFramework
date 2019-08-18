package com.tanjiajun.androidgenericframework.data.network

import com.tanjiajun.androidgenericframework.data.model.request.LoginRequestData
import com.tanjiajun.androidgenericframework.data.model.response.UserInfoData
import com.tanjiajun.androidgenericframework.data.network.retrofit.UserRetrofit

/**
 * Created by TanJiaJun on 2019-08-01.
 */
class UserNetwork private constructor() : BaseNetwork() {

    private val userService = UserRetrofit.instance.userService

    suspend fun login(phoneNumber: String, password: String) =
            userService.login(LoginRequestData(phoneNumber, password)).await(UserInfoData::class.java)

    companion object {
        val instance by lazy { UserNetwork() }
    }

}