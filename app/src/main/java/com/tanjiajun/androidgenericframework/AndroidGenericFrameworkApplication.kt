package com.tanjiajun.androidgenericframework

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.tanjiajun.androidgenericframework.data.dao.UserDao
import com.tanjiajun.androidgenericframework.data.network.UserNetwork
import com.tanjiajun.androidgenericframework.data.repository.UserRepository

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class AndroidGenericFrameworkApplication : MultiDexApplication() {

    val userRepository: UserRepository
        get() = UserRepository.getInstance(UserNetwork.instance, UserDao())

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}
