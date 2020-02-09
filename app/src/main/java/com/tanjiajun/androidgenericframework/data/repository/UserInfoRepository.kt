package com.tanjiajun.androidgenericframework.data.repository

import com.tanjiajun.androidgenericframework.data.dao.user.UserDao
import com.tanjiajun.androidgenericframework.data.model.user.response.UserAccessTokenData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import com.tanjiajun.androidgenericframework.data.network.user.UserNetwork

/**
 * Created by TanJiaJun on 2019-07-31.
 */
class UserInfoRepository private constructor(
        private val network: UserNetwork,
        private val dao: UserDao
) {

    fun isLogin(): Boolean =
            dao.userId != -1

    fun cacheUsername(username: String) =
            dao.cacheUsername(username)

    fun cachePassword(password: String) =
            dao.cachePassword(password)

    suspend fun authorizations(): UserAccessTokenData =
            network.authorizations()

    suspend fun getUserInfo(): UserInfoData =
            network.fetchUserInfo()

    fun cacheUserId(userId: Int) =
            dao.cacheUserId(userId)

    fun getName(): String =
            dao.name

    fun cacheName(name: String) =
            dao.cacheName(name)

    fun getAvatarUrl(): String =
            dao.avatarUrl

    fun cacheAvatarUrl(avatarUrl: String) =
            dao.cacheAvatarUrl(avatarUrl)

    fun logout() =
            dao.clearUserInfoCache()

    companion object {
        @Volatile
        private var instance: UserInfoRepository? = null

        fun getInstance(
                network: UserNetwork,
                dao: UserDao
        ): UserInfoRepository =
                instance ?: synchronized(this) {
                    instance ?: UserInfoRepository(network, dao).also { instance = it }
                }
    }

}