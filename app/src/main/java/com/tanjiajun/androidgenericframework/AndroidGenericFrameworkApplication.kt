package com.tanjiajun.androidgenericframework

import com.tanjiajun.androidgenericframework.di.DaggerApplicationComponent
import com.tencent.mmkv.MMKV
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class AndroidGenericFrameworkApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.factory().create(applicationContext)

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
    }

    companion object {
        lateinit var instance: AndroidGenericFrameworkApplication
    }

}