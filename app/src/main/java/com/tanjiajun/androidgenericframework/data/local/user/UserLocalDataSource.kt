package com.tanjiajun.androidgenericframework.data.local.user

import com.tanjiajun.androidgenericframework.utils.int
import com.tanjiajun.androidgenericframework.utils.string
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * Created by TanJiaJun on 2019-08-08.
 */
class UserLocalDataSource @Inject constructor(
        private val mmkv: MMKV
) {

    var accessToken by mmkv.string("user_access_token", "")
    var userId by mmkv.int("user_id", -1)
    var username by mmkv.string("username", "")
    var password by mmkv.string("password", "")
    var name by mmkv.string("name", "")
    var avatarUrl by mmkv.string("avatar_url", "")

    fun clearUserInfoCache() =
            with(mmkv) {
                removeValuesForKeys(arrayOf(
                        "user_access_token",
                        "user_id",
                        "username",
                        "password",
                        "name",
                        "avatar_url"
                ))
            }

    fun cacheUserId(userId: Int) {
        this.userId = userId
    }

    fun cacheUsername(username: String) {
        this.username = username
    }

    fun cachePassword(password: String) {
        this.password = password
    }

    fun cacheName(name: String) {
        this.name = name
    }

    fun cacheAvatarUrl(avatarUrl: String) {
        this.avatarUrl = avatarUrl
    }

}