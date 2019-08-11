package com.tanjiajun.dadarecycle.data.network

import com.google.gson.Gson
import com.google.gson.JsonObject
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

    protected suspend fun <T> Call<JsonObject>.await(clazz: Class<T>): T =
            suspendCoroutine { continuation ->
                enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) =
                            response.body()?.run {
                                get("code")
                                        ?.asInt
                                        ?.takeIf { it == 0 }
                                        ?.let {
                                            continuation.resume(Gson().fromJson(get("data"), clazz))
                                        }
                                        ?: continuation.resumeWithException(RuntimeException(get("data")?.asString))
                            } ?: continuation.resumeWithException(RuntimeException("服务器异常"))

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) =
                            continuation.resumeWithException(t)
                })
            }

}