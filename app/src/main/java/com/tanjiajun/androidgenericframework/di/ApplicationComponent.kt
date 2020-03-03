package com.tanjiajun.androidgenericframework.di

import android.content.Context
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by TanJiaJun on 2020/3/4.
 */
@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            ViewModelFactory::class
        ]
)
interface ApplicationComponent : AndroidInjector<AndroidGenericFrameworkApplication> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Context): AndroidGenericFrameworkApplication

    }

}