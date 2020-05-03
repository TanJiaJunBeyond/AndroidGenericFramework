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
@Module
open class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(userDao: UserDao): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(AndroidGenericFrameworkConfiguration.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(AndroidGenericFrameworkConfiguration.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(BasicAuthInterceptor(userDao))
                    .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(String.format("%1\$s://%2\$s/", SCHEMA_HTTPS, AndroidGenericFrameworkConfiguration.HOST))
                    .build()

    @Singleton
    @Provides
    fun provideUserApiClient(retrofit: Retrofit): UserApiClient =
            UserApiClient(retrofit)

    @Singleton
    @Provides
    fun provideRepositoryApiClient(retrofit: Retrofit): RepositoryApiClient =
            RepositoryApiClient(retrofit)

    private companion object {
        const val SCHEMA_HTTPS = "https"
    }

}