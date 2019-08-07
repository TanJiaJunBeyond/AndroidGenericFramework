package com.tanjiajun.dadarecycle

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.tanjiajun.dadarecycle.data.dao.UserDao
import com.tanjiajun.dadarecycle.data.network.UserNetwork
import com.tanjiajun.dadarecycle.data.repository.UserRepository
import org.litepal.LitePal

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class DaDaRecycleApplication : MultiDexApplication() {

    val userRepository: UserRepository
        get() = UserRepository.getInstance(UserNetwork.instance, UserDao())

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}