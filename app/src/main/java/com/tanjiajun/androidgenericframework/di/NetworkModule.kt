package com.tanjiajun.androidgenericframework.di

import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkConfiguration
import com.tanjiajun.androidgenericframework.data.apiclient.BasicAuthInterceptor
import com.tanjiajun.androidgenericframework.data.apiclient.repository.RepositoryApiClient
import com.tanjiajun.androidgenericframework.data.apiclient.user.UserApiClient
import com.tanjiajun.androidgenericframework.data.dao.user.UserDao
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
    fun provideOkHttpClient(userDao: UserDao): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(AndroidGenericFrameworkConfiguration.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(AndroidGenericFrameworkConfiguration.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(BasicAuthInterceptor(userDao))
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
    fun provideUserApiClient(retrofit: Retrofit): UserApiClient =
            UserApiClient(retrofit)

    @Provides
    @Singleton
    fun provideRepositoryApiClient(retrofit: Retrofit): RepositoryApiClient =
            RepositoryApiClient(retrofit)

}