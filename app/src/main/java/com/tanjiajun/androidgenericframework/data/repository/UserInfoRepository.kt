package com.tanjiajun.androidgenericframework.data.repository

import com.tanjiajun.androidgenericframework.data.local.user.UserLocalDataSource
import com.tanjiajun.androidgenericframework.data.model.user.response.UserAccessTokenData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import com.tanjiajun.androidgenericframework.data.remote.user.UserRemoteDataSource

/**
 * Created by TanJiaJun on 2019-07-31.
 */
class UserInfoRepository(
        private val remoteDataSource: UserRemoteDataSource,
        private val localDataSource: UserLocalDataSource
) {

    fun isLogin(): Boolean =
            localDataSource.userId != -1

    fun cacheUsername(username: String) =
            localDataSource.cacheUsername(username)

    fun cachePassword(password: String) =
            localDataSource.cachePassword(password)

    suspend fun authorizations(): UserAccessTokenData =
            remoteDataSource.authorizations()

    suspend fun getUserInfo(): UserInfoData =
            remoteDataSource.fetchUserInfo()

    fun cacheUserId(userId: Int) =
            localDataSource.cacheUserId(userId)

    fun getName(): String =
            localDataSource.name

    fun cacheName(name: String) =
            localDataSource.cacheName(name)

    fun getAvatarUrl(): String =
            localDataSource.avatarUrl

    fun cacheAvatarUrl(avatarUrl: String) =
            localDataSource.cacheAvatarUrl(avatarUrl)

    fun logout() =
            localDataSource.clearUserInfoCache()

}