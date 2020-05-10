package com.tanjiajun.androidgenericframework.data.remote.user

import com.tanjiajun.androidgenericframework.data.model.user.request.LoginRequestData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserAccessTokenData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by TanJiaJun on 2020/5/6.
 */
class UserRemoteDataSource(
        retrofit: Retrofit
) {

    private val service: Service = retrofit.create(Service::class.java)

    suspend fun authorizations(): UserAccessTokenData =
            service.authorizations(LoginRequestData.generate())

    suspend fun fetchUserInfo(): UserInfoData =
            service.fetchUserInfo()

    interface Service {

        @POST
        @Headers("Accept: application/json")
        suspend fun authorizations(@Body loginRequestData: LoginRequestData): UserAccessTokenData

        @GET("user")
        suspend fun fetchUserInfo(): UserInfoData

    }

}