package com.tanjiajun.androidgenericframework.data.repository

import com.tanjiajun.androidgenericframework.data.dao.UserDao
import com.tanjiajun.androidgenericframework.data.model.response.UserInfoData
import com.tanjiajun.androidgenericframework.data.network.UserNetwork
import com.tanjiajun.androidgenericframework.utils.gsonFromJson
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
                // TODO 因为后端原因，暂时缓存本地数据
                dao.cacheUserInfo(gsonFromJson("{\n" +
                        "    \"headPortraitUrl\":\"\",\n" +
                        "    \"userName\":\"谭嘉俊\",\n" +
                        "    \"gender\":\"男\",\n" +
                        "    \"age\":25\n" +
                        "}"))
                network.login(phoneNumber, password)
            }

    fun getUserInfo(): UserInfoData? =
            dao.getCachedUserInfo()

    fun logout() {
        dao.clearUserInfoCache()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
                network: UserNetwork,
                dao: UserDao
        ): UserRepository =
                instance ?: synchronized(this) {
                    instance ?: UserRepository(network, dao).also { instance = it }
                }
    }

}