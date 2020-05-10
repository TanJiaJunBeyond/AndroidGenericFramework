package com.tanjiajun.androidgenericframework.di

import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkConfiguration
import com.tanjiajun.androidgenericframework.data.local.user.UserLocalDataSource
import com.tanjiajun.androidgenericframework.data.remote.BasicAuthInterceptor
import com.tanjiajun.androidgenericframework.data.remote.repository.RepositoryRemoteDataSource
import com.tanjiajun.androidgenericframework.data.remote.user.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by TanJiaJun on 2020/4/4.
 */
@Suppress("unused")
@Module
open class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(localDataSource: UserLocalDataSource): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(AndroidGenericFrameworkConfiguration.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(AndroidGenericFrameworkConfiguration.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(BasicAuthInterceptor(localDataSource))
                    .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(String.format("%1\$s://%2\$s/", "https", AndroidGenericFrameworkConfiguration.HOST))
                    .build()

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(retrofit: Retrofit): UserRemoteDataSource =
            UserRemoteDataSource(retrofit)

    @Provides
    @Singleton
    fun provideRepositoryRemoteDataSource(retrofit: Retrofit): RepositoryRemoteDataSource =
            RepositoryRemoteDataSource(retrofit)

}