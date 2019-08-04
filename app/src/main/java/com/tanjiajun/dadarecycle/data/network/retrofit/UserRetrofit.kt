package com.tanjiajun.dadarecycle.data.network.retrofit

import com.tanjiajun.dadarecycle.data.network.retrofit.services.UserService

/**
 * Created by TanJiaJun on 2019-07-30.
 */
class UserRetrofit : BaseRetrofit() {

    var userService: UserService = retrofit.create(UserService::class.java)

    companion object {
        val instance by lazy { UserRetrofit() }
    }

}