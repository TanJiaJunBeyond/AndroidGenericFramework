package com.tanjiajun.androidgenericframework.data.apiclient

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLException

/**
 * Created by TanJiaJun on 2020-02-04.
 */
object ExceptionHandler {

    fun handleException(throwable: Throwable): ResponseThrowable =
            when (throwable) {
                is JsonParseException ->
                    ResponseThrowable(errorCode = 0, errorMessage = "JsonParseException", throwable = throwable)

                is JSONException ->
                    ResponseThrowable(errorCode = 0, errorMessage = "JSONException", throwable = throwable)

                is ParseException ->
                    ResponseThrowable(errorCode = 0, errorMessage = "ParseException", throwable = throwable)

                is MalformedJsonException ->
                    ResponseThrowable(errorCode = 0, errorMessage = "MalformedJsonException", throwable = throwable)

                is ConnectException ->
                    ResponseThrowable(errorCode = 0, errorMessage = "ConnectException", throwable = throwable)

                is HttpException ->
                    ResponseThrowable(errorCode = throwable.code(), errorMessage = throwable.message(), throwable = throwable)

                is SSLException ->
                    ResponseThrowable(errorCode = 0, errorMessage = "SSLException", throwable = throwable)

                is SocketTimeoutException ->
                    ResponseThrowable(errorCode = 0, errorMessage = "SocketTimeoutException", throwable = throwable)

                is UnknownHostException ->
                    ResponseThrowable(errorCode = 0, errorMessage = "UnknownHostException", throwable = throwable)

                else ->
                    ResponseThrowable(errorCode = 0, errorMessage = "UnknownError", throwable = throwable)
            }

}