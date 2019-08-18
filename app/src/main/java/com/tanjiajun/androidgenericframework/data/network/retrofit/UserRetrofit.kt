package com.tanjiajun.androidgenericframework.data.network.retrofit

import com.tanjiajun.androidgenericframework.data.network.retrofit.services.UserService

/**
 * Created by TanJiaJun on 2019-07-30.
 */
class UserRetrofit : BaseRetrofit() {

    var userService: UserService = retrofit.create(UserService::class.java)

    companion object {
        val instance by lazy { UserRetrofit() }
    }

}