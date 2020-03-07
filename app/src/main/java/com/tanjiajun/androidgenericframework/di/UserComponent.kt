package com.tanjiajun.androidgenericframework.di

import androidx.lifecycle.ViewModel
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.LoginViewModel
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.PersonalCenterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by TanJiaJun on 2020/3/8.
 */
@Module
abstract class UserComponent {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonalCenterViewModel::class)
    abstract fun bindPersonalCenterViewModel(viewModel: PersonalCenterViewModel): ViewModel

}