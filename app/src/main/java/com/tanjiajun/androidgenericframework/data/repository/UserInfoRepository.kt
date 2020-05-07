package com.tanjiajun.androidgenericframework.data.repository

import com.tanjiajun.androidgenericframework.data.apiclient.user.UserApiClient
import com.tanjiajun.androidgenericframework.data.dao.user.UserDao
import com.tanjiajun.androidgenericframework.data.model.user.response.UserAccessTokenData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData

/**
 * Created by TanJiaJun on 2019-07-31.
 */
class UserInfoRepository(
        private val apiClient: UserApiClient,
        private val dao: UserDao
) {

    fun isLogin(): Boolean =
            dao.userId != -1

    fun cacheUsername(username: String) =
            dao.cacheUsername(username)

    fun cachePassword(password: String) =
            dao.cachePassword(password)

    suspend fun authorizations(): UserAccessTokenData =
            apiClient.authorizations()

    suspend fun getUserInfo(): UserInfoData =
            apiClient.fetchUserInfo()

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

}