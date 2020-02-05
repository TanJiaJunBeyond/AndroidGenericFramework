package com.tanjiajun.androidgenericframework.data.model.user.request

import com.google.gson.annotations.SerializedName
import com.tanjiajun.androidgenericframework.BuildConfig

/**
 * Created by TanJiaJun on 2019-08-02.
 */
data class LoginRequestData(
        val scopes: List<String>,
        val note: String,
        @SerializedName("client_id") val clientId: String,
        @SerializedName("client_secret") val clientSecret: String
) {

    companion object {
        fun generate(): LoginRequestData =
                LoginRequestData(
                        scopes = listOf("user", "repo", "gist", "notifications"),
                        note = BuildConfig.APPLICATION_ID,
                        clientId = BuildConfig.GITHUB_CLIENT_ID,
                        clientSecret = BuildConfig.GITHUB_CLIENT_SECRET
                )
    }

}