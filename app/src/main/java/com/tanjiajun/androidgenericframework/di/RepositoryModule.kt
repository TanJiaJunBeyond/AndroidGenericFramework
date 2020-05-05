package com.tanjiajun.androidgenericframework.di

import com.tanjiajun.androidgenericframework.data.apiclient.repository.RepositoryApiClient
import com.tanjiajun.androidgenericframework.data.apiclient.user.UserApiClient
import com.tanjiajun.androidgenericframework.data.dao.user.UserDao
import com.tanjiajun.androidgenericframework.data.repository.GitHubRepository
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by TanJiaJun on 2020/5/6.
 */
@Suppress("unused")
@Module
open class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryOfGitHubRepository(apiClient: RepositoryApiClient): GitHubRepository =
            GitHubRepository(apiClient)

    @Provides
    @Singleton
    fun provideUserInfoRepository(apiClient: UserApiClient,
                                  dao: UserDao): UserInfoRepository =
            UserInfoRepository(apiClient, dao)

}