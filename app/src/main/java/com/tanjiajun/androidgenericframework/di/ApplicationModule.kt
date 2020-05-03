package com.tanjiajun.androidgenericframework.di

import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkConfiguration
import com.tanjiajun.androidgenericframework.data.apiclient.repository.RepositoryApiClient
import com.tanjiajun.androidgenericframework.data.apiclient.user.UserApiClient
import com.tanjiajun.androidgenericframework.data.dao.user.UserDao
import com.tanjiajun.androidgenericframework.data.repository.RepositoryOfGitHubRepository
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tencent.mmkv.MMKV
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by TanJiaJun on 2020/3/4.
 */
@Module
open class ApplicationModule {

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
    fun provideRepositoryOfGitHubRepository(apiClient: RepositoryApiClient): RepositoryOfGitHubRepository =
            RepositoryOfGitHubRepository(apiClient)

    @Provides
    @Singleton
    fun provideUserInfoRepository(apiClient: UserApiClient,
                                  dao: UserDao): UserInfoRepository =
            UserInfoRepository(apiClient, dao)

}