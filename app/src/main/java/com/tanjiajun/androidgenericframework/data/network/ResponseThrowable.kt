package com.tanjiajun.androidgenericframework.data.network

/**
 * Created by TanJiaJun on 2020-02-04.
 */
class ResponseThrowable(
        var errorCode: Int,
        var errorMessage: String,
        throwable: Throwable
) : Exception(throwable)