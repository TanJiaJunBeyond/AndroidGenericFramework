package com.tanjiajun.androidgenericframework.data.network

/**
 * Created by TanJiaJun on 2020-02-04.
 */
enum class Error(
        val code: Int,
        val errorMessage: String
) {

    UNKNOWN_ERROR(code = 1000, errorMessage = "未知错误"),
    PARSE_ERROR(code = 1001, errorMessage = "解析错误"),
    NETWORK_ERROR(code = 1002, errorMessage = "网络错误"),
    HTTP_ERROR(code = 1003, errorMessage = "协议错误"),
    SSL_ERROR(code = 1004, errorMessage = "证书错误"),
    TIMEOUT_ERROR(code = 1006, errorMessage = "连接超时")

}