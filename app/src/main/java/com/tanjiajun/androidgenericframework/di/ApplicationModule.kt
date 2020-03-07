package com.tanjiajun.androidgenericframework.di

import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkConfiguration
import com.tanjiajun.androidgenericframework.data.dao.user.UserDao
import com.tanjiajun.androidgenericframework.data.network.repository.RepositoryNetwork
import com.tanjiajun.androidgenericframework.data.network.user.UserNetwork
import com.tencent.mmkv.MMKV
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by TanJiaJun on 2020/3/4.
 */
@Module
object ApplicationModule {

    @Provides
    @Singleton
    fun provideUserDao(): UserDao =
            UserDao(MMKV.mmkvWithID(
                    AndroidGenericFrameworkConfiguration.MMKV_ID,
                    MMKV.SINGLE_PROCESS_MODE,
                    AndroidGenericFrameworkConfiguration.MMKV_CRYPT_KEY
            ))

    @Provides
    @Singleton
    fun provideUserNetwork(): UserNetwork =
            UserNetwork()

    @Provides
    @Singleton
    fun provideRepositoryNetwork(): RepositoryNetwork =
            RepositoryNetwork()

}