package com.tanjiajun.androidgenericframework.data.repository

import com.tanjiajun.androidgenericframework.data.dao.UserDao
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import com.tanjiajun.androidgenericframework.data.network.UserNetwork
import com.tanjiajun.androidgenericframework.utils.gsonFromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by TanJiaJun on 2019-07-31.
 */
class UserInfoRepository private constructor(
        private val network: UserNetwork,
        private val dao: UserDao
) {

    fun isUserInfoCached(): Boolean =
            dao.getCachedUserInfo() != null

    suspend fun login(phoneNumber: String,
                      password: String) =
            withContext(Dispatchers.IO) {
                // TODO 因为后端原因，暂时缓存本地数据
                dao.cacheUserInfo(gsonFromJson("{\n" +
                        "    \"headPortraitUrl\":\"https://qa-media-api.xogrp.com/images/3932fa03-2437-4ca2-b413-b88ac261bc0f\",\n" +
                        "    \"userName\":\"谭嘉俊\",\n" +
                        "    \"gender\":\"男\",\n" +
                        "    \"age\":25\n" +
                        "}"))
                network.login(phoneNumber, password)
            }

    fun getUserInfo(): UserInfoData? =
            dao.getCachedUserInfo()

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