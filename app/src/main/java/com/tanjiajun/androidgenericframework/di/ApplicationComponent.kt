package com.tanjiajun.androidgenericframework.di

import android.content.Context
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by TanJiaJun on 2020/3/4.
 */
@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ApplicationModule::class,
            NetworkModule::class,
            MainModule::class,
            UserModule::class,
            RepositoryModule::class
        ]
)
interface ApplicationComponent : AndroidInjector<AndroidGenericFrameworkApplication> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Context): ApplicationComponent

    }

}