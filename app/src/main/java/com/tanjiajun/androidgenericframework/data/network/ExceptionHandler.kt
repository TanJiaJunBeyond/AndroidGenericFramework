package com.tanjiajun.androidgenericframework.data.network

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import com.tanjiajun.androidgenericframework.utils.otherwise
import com.tanjiajun.androidgenericframework.utils.yes
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
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException ->
                    ResponseThrowable(error = Error.PARSE_ERROR, throwable = throwable)

                is ConnectException ->
                    ResponseThrowable(error = Error.NETWORK_ERROR, throwable = throwable)

                is HttpException ->
                    ResponseThrowable(error = Error.HTTP_ERROR, throwable = throwable)

                is SSLException ->
                    ResponseThrowable(error = Error.SSL_ERROR, throwable = throwable)

                is SocketTimeoutException, is UnknownHostException ->
                    ResponseThrowable(error = Error.TIMEOUT_ERROR, throwable = throwable)

                else ->
                    (throwable.message.isNullOrEmpty())
                            .yes { ResponseThrowable(code = 1000, throwable = throwable) }
                            .otherwise { ResponseThrowable(error = Error.UNKNOWN_ERROR, throwable = throwable) }
            }

}