package com.tanjiajun.dadarecycle.data.repository

import com.tanjiajun.dadarecycle.data.dao.UserDao
import com.tanjiajun.dadarecycle.data.network.UserNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by TanJiaJun on 2019-07-31.
 */
class UserRepository private constructor(
        private val network: UserNetwork,
        private val dao: UserDao
) {

    fun isUserInfoCached(): Boolean =
            dao.getCachedUserInfo() != null

    suspend fun login(phoneNumber: String,
                      password: String) =
            withContext(Dispatchers.IO) {
                val userInfoData = network.login(phoneNumber, password)
                dao.cacheUserInfo(userInfoData)
                userInfoData
            }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
                network: UserNetwork,
                dao: UserDao
        ): UserRepository =
                instance ?: synchronized(this) {
                    instance
                            ?: UserRepository(network, dao).also { instance = it }
                }
    }

}