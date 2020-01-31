package com.tanjiajun.androidgenericframework

import android.annotation.SuppressLint
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

    val userRepository: UserInfoRepository
        get() = UserInfoRepository.getInstance(
                network = UserNetwork.instance,
                dao = UserDao(),
                mmkv = MMKV.mmkvWithID(MMKV_ID, MMKV.SINGLE_PROCESS_MODE, MMKV_CRYPT_KEY)
        )

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}
