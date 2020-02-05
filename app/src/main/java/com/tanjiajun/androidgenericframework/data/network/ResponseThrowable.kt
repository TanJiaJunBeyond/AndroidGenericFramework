package com.tanjiajun.androidgenericframework.data.network

/**
 * Created by TanJiaJun on 2020-02-04.
 */
class ResponseThrowable(
        error: Error? = null,
        val code: Int? = null,
        val errorMessage: String? = null,
        throwable: Throwable? = null
) : Exception(throwable)