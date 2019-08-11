package com.tanjiajun.dadarecycle.data.dao

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.tanjiajun.dadarecycle.DaDaRecycleApplication
import com.tanjiajun.dadarecycle.data.model.response.UserInfoData
import com.tanjiajun.dadarecycle.utils.gsonFromJson

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
            PreferenceManager.getDefaultSharedPreferences(DaDaRecycleApplication.context).edit {
                putString("userInfo", Gson().toJson(userInfoData))
            } ?: Unit

    fun getCachedUserInfo(): UserInfoData? =
            PreferenceManager
                    .getDefaultSharedPreferences(DaDaRecycleApplication.context)
                    .getString("userInfo", null)
                    ?.let {
                        gsonFromJson(it)
                    }

}