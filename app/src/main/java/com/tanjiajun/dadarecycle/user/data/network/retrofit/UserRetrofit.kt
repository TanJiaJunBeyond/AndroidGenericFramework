package com.tanjiajun.dadarecycle.user.data.network.retrofit

import com.tanjiajun.dadarecycle.user.data.network.retrofit.services.UserService

/**
 * Created by TanJiaJun on 2019-07-30.
 */
class UserRetrofit : BaseRetrofit() {

    lateinit var userService: UserService

    override fun initService() {
        userService = retrofit.create(UserService::class.java)
    }

    companion object {
        val instance by lazy { UserRetrofit() }
    }

}