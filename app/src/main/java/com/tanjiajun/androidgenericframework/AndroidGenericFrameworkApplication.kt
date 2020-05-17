package com.tanjiajun.androidgenericframework

import androidx.multidex.MultiDexApplication
import com.tanjiajun.androidgenericframework.di.applicationModules
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class AndroidGenericFrameworkApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AndroidGenericFrameworkApplication)
            androidFileProperties()
            fragmentFactory()
            modules(applicationModules)
        }
    }

    companion object {
        lateinit var instance: AndroidGenericFrameworkApplication
    }

}