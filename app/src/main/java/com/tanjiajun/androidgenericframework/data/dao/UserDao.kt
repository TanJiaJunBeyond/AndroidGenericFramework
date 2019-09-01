package com.tanjiajun.androidgenericframework.data.dao

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkApplication
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import com.tanjiajun.androidgenericframework.utils.gsonFromJson

/**
 * Created by TanJiaJun on 2019-08-08.
 */
class UserDao {

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

}