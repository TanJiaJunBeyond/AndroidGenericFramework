package com.tanjiajun.androidgenericframework.di

import com.tanjiajun.androidgenericframework.data.local.user.UserLocalDataSource
import com.tanjiajun.androidgenericframework.data.remote.repository.RepositoryRemoteDataSource
import com.tanjiajun.androidgenericframework.data.remote.user.UserRemoteDataSource
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
    fun provideGitHubRepository(remoteDataSource: RepositoryRemoteDataSource): GitHubRepository =
            GitHubRepository(remoteDataSource)

    @Provides
    @Singleton
    fun provideUserInfoRepository(remoteDataSource: UserRemoteDataSource,
                                  localDataSource: UserLocalDataSource): UserInfoRepository =
            UserInfoRepository(remoteDataSource, localDataSource)

}