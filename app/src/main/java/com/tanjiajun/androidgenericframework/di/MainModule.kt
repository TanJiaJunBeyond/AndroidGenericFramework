package com.tanjiajun.androidgenericframework.di

import androidx.lifecycle.ViewModel
import com.tanjiajun.androidgenericframework.ui.main.activity.MainActivity
import com.tanjiajun.androidgenericframework.ui.main.activity.SplashActivity
import com.tanjiajun.androidgenericframework.ui.main.viewModel.MainViewModel
import com.tanjiajun.androidgenericframework.ui.main.viewModel.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by TanJiaJun on 2020/5/3.
 */
@Suppress("unused")
@Module
abstract class MainModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun contributeSplashActivity(): SplashActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

}