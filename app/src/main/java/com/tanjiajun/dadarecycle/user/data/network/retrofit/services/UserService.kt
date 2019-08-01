package com.tanjiajun.dadarecycle.user.data.network.retrofit.services

import com.tanjiajun.dadarecycle.user.data.model.request.LoginRequestData
import com.tanjiajun.dadarecycle.user.data.model.response.UserInfoData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

/**
 * Created by TanJiaJun on 2019-07-30.
 */
interface UserService {

    @GET("/v1/user/sign")
    fun login(@Body loginRequestData: LoginRequestData): Call<UserInfoData>

}