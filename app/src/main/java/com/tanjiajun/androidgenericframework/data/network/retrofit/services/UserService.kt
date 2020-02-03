package com.tanjiajun.androidgenericframework.data.network.retrofit.services

import com.google.gson.JsonObject
import com.tanjiajun.androidgenericframework.data.model.user.request.LoginRequestData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserAccessTokenData
import com.tanjiajun.androidgenericframework.data.model.user.response.UserInfoData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by TanJiaJun on 2019-07-30.
 */
interface UserService {

    @POST("authorizations")
    @Headers("Accept: application/json")
    suspend fun authorizations(@Body loginRequestData: LoginRequestData): UserAccessTokenData

    @POST("user/v1/user/sign")
    fun login(@Body loginRequestData: LoginRequestData): Call<JsonObject>

    @GET("user")
    suspend fun fetchUserInfo(): UserInfoData

}