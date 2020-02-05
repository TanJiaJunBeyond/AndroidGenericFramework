package com.tanjiajun.androidgenericframework

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkConfiguration.MMKV_CRYPT_KEY
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkConfiguration.MMKV_ID
import com.tanjiajun.androidgenericframework.data.dao.UserDao
import com.tanjiajun.androidgenericframework.data.network.UserNetwork
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tencent.mmkv.MMKV

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class AndroidGenericFrameworkApplication : MultiDexApplication() {

    lateinit var userDao: UserDao
    lateinit var userRepository: UserInfoRepository

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = this

        MMKV.initialize(this)

        userDao = UserDao(MMKV.mmkvWithID(MMKV_ID, MMKV.SINGLE_PROCESS_MODE, MMKV_CRYPT_KEY))
        userRepository = UserInfoRepository.getInstance(network = UserNetwork.instance, dao = userDao)
    }

    companion object {
        lateinit var instance: AndroidGenericFrameworkApplication
        lateinit var context: Context
    }

}
