package com.tanjiajun.dadarecycle.user.data.repository

import com.tanjiajun.dadarecycle.user.data.network.UserNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by TanJiaJun on 2019-07-31.
 */
class UserRepository private constructor(private val network: UserNetwork) {

    suspend fun login(
        phoneNumber: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        network.login(phoneNumber, password)
    }

    companion object {
        val instance by lazy { UserRepository }
    }

}