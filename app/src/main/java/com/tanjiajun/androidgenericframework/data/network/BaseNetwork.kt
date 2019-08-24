package com.tanjiajun.androidgenericframework.data.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by TanJiaJun on 2019-08-01.
 */
abstract class BaseNetwork {

    protected suspend fun <T> Call<T>.await(): T =
            suspendCoroutine { continuation ->
                enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>, response: Response<T>) =
                            response
                                    .body()
                                    ?.let { continuation.resume(it) }
                                    ?: continuation.resumeWithException(
                                            RuntimeException("Response body is null.")
                                    )

                    override fun onFailure(call: Call<T>, t: Throwable) =
                            continuation.resumeWithException(t)
                })
            }

}