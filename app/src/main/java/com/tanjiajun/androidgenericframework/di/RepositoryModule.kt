package com.tanjiajun.androidgenericframework.di

import androidx.lifecycle.ViewModel
import com.tanjiajun.androidgenericframework.ui.repository.fragment.RepositoryFragment
import com.tanjiajun.androidgenericframework.ui.repository.viewmodel.RepositoryViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by TanJiaJun on 2020/3/8.
 */
@Suppress("unused")
@Module
abstract class RepositoryModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun contributeRepositoryFragment(): RepositoryFragment

    @Binds
    @IntoMap
    @ViewModelKey(RepositoryViewModel::class)
    internal abstract fun bindRepositoryViewModel(viewModel: RepositoryViewModel): ViewModel

}