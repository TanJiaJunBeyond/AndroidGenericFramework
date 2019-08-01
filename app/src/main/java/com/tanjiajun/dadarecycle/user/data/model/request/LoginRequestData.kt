package com.tanjiajun.dadarecycle.user.data.model.request

import com.google.gson.annotations.SerializedName

/**
 * Created by TanJiaJun on 2019-08-02.
 */
data class LoginRequestData(
    @SerializedName("phone") var phoneNumber: String,
    var password: String
)