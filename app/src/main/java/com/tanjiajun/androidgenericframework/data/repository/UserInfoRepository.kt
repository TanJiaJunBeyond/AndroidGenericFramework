package com.tanjiajun.androidgenericframework.data.repository

import com.tanjiajun.androidgenericframework.data.dao.UserDao
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

    fun cacheUserInfo(username: String, password: String) =
            dao.cacheUserInfo(username, password)

    fun isUserInfoCached(): Boolean =
            dao.getCachedUserInfo() != null

    suspend fun authorizations(): UserAccessTokenData =
            network.authorizations()

    suspend fun getUserInfo(): UserInfoData =
            network.fetchUserInfo()

    fun logout() =
            dao.clearUserInfoCache() ?: Unit

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