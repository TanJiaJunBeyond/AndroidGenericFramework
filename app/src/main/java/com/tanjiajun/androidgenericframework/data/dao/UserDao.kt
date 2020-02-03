package com.tanjiajun.androidgenericframework.data.dao

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkApplication
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import com.tanjiajun.androidgenericframework.utils.gsonFromJson
import com.tanjiajun.mmkvdemo.utils.boolean
import com.tanjiajun.mmkvdemo.utils.string
import com.tencent.mmkv.MMKV

/**
 * Created by TanJiaJun on 2019-08-08.
 */
class UserDao(
        mmkv: MMKV
) {

    var accessToken by mmkv.string("user_access_token", "")
    var username by mmkv.string("username", "")
    var password by mmkv.string("password", "")
    var isAutoLogin by mmkv.boolean("is_auto_login")

    private fun SharedPreferences.edit(function: SharedPreferences.Editor.() -> Unit) =
            edit().also {
                function(it)
                it.apply()
            }

    fun cacheUserInfo(userInfoData: UserInfoData) =
            PreferenceManager.getDefaultSharedPreferences(AndroidGenericFrameworkApplication.context).edit {
                putString("userInfo", Gson().toJson(userInfoData))
            } ?: Unit

    fun getCachedUserInfo(): UserInfoData? =
            PreferenceManager
                    .getDefaultSharedPreferences(AndroidGenericFrameworkApplication.context)
                    .getString("userInfo", null)
                    ?.let {
                        gsonFromJson(it)
                    }

    fun clearUserInfoCache() =
            PreferenceManager.getDefaultSharedPreferences(AndroidGenericFrameworkApplication.context).edit {
                clear()
            }

    fun cacheUserInfo(username: String, password: String) {
        this.username = username
        this.password = password
    }


}