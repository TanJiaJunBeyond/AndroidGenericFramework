package com.tanjiajun.dadarecycle

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import org.litepal.LitePal

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class DaDaRecycleApplication : MultiDexApplication() {

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