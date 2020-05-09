package com.tanjiajun.androidgenericframework.di

import androidx.lifecycle.ViewModel
import com.tanjiajun.androidgenericframework.ui.user.activity.PersonalCenterActivity
import com.tanjiajun.androidgenericframework.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.androidgenericframework.ui.user.fragment.LoginFragment
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.LoginViewModel
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.PersonalCenterViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by TanJiaJun on 2020/3/8.
 */
@Suppress("unused")
@Module
abstract class UserModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun contributeRegisterAndLoginActivity(): RegisterAndLoginActivity

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun contributePersonalCenterActivity(): PersonalCenterActivity

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonalCenterViewModel::class)
    internal abstract fun bindPersonalCenterViewModel(viewModel: PersonalCenterViewModel): ViewModel

}