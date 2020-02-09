package com.tanjiajun.androidgenericframework.data.network

import android.util.Base64
import com.tanjiajun.androidgenericframework.data.dao.user.UserDao
import com.tanjiajun.androidgenericframework.utils.otherwise
import com.tanjiajun.androidgenericframework.utils.yes
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by TanJiaJun on 2020-02-01.
 */
class BasicAuthInterceptor(
        private val dao: UserDao
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
            with(chain) {
                var request = request()
                val authorization = getAuthorization()
                (authorization.isNotEmpty())
                        .yes {
                            request = request
                                    .newBuilder()
                                    .addHeader("Authorization", authorization)
                                    .url(request.url().toString())
                                    .build()
                        }
                proceed(request)
            }

    private fun getAuthorization(): String =
            with(dao) {
                (accessToken.isBlank())
                        .yes {
                            (username.isNotBlank() && password.isNotBlank())
                                    .yes {
                                        "basic " + Base64.encodeToString((
                                                "$username:$password").toByteArray(),
                                                Base64.NO_WRAP
                                        )
                                    }
                                    .otherwise { "" }
                        }
                        .otherwise { "token $accessToken" }
            }

}