package com.tanjiajun.androidgenericframework.data.network.retrofit

import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkApplication
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkConfiguration
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by TanJiaJun on 2019-07-30.
 */
abstract class BaseRetrofit {

    open var schema = SCHEMA_HTTPS
    open var host = AndroidGenericFrameworkConfiguration.HOST
    open var connectTimeout = AndroidGenericFrameworkConfiguration.CONNECT_TIMEOUT
    open var readTimeout = AndroidGenericFrameworkConfiguration.READ_TIMEOUT

    private var builder =
            Retrofit.Builder()
                    .client(getOkHttpClient().build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())

    var retrofit: Retrofit = builder.baseUrl(getBaseUrl()).build()

    private fun getBaseUrl() = String.format("%1\$s://%2\$s/", schema, host)

    private fun getOkHttpClient() =
            OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                    .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                    .addInterceptor(BasicAuthInterceptor(
                            dao = AndroidGenericFrameworkApplication.instance.userDao
                    ))

    open fun addHeader(builder: Request.Builder) =
            builder.apply {
            }

    private companion object {
        const val SCHEMA_HTTPS = "https"
    }

}