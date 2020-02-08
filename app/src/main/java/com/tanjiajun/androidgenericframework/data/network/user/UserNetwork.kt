package com.tanjiajun.androidgenericframework.data.network.user

import com.tanjiajun.androidgenericframework.data.model.user.request.LoginRequestData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserAccessTokenData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import com.tanjiajun.androidgenericframework.data.network.BaseRetrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by TanJiaJun on 2020-02-07.
 */
class UserNetwork private constructor() {

    private val service by lazy { UserRetrofit().service }

    suspend fun authorizations(): UserAccessTokenData =
            service.authorizations(LoginRequestData.generate())

    suspend fun fetchUserInfo(): UserInfoData =
            service.fetchUserInfo()

    interface UserService {

        @POST("authorizations")
        @Headers("Accept: application/json")
        suspend fun authorizations(@Body loginRequestData: LoginRequestData): UserAccessTokenData

        @GET("user")
        suspend fun fetchUserInfo(): UserInfoData

    }

    inner class UserRetrofit : BaseRetrofit() {

        var service: UserService = retrofit.create(UserService::class.java)

    }

    companion object {
        val instance by lazy { UserNetwork() }
    }

}