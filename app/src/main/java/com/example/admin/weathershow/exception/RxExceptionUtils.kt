package com.example.admin.weathershow.exception

import org.json.JSONException
import retrofit2.HttpException

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * 对返回的错误进行处理
 */
object RxExceptionUtils {
    fun handleException(e: Throwable): String {
        var error = "未知错误"
        if (e is UnknownHostException) {
            error = "网络不可用"
        } else if (e is SocketTimeoutException) {
            error = "请求网络超时"
        } else if (e is HttpException) {
            error = convertHttpCode(e)
        } else if (e is ParseException || e is JSONException) {
            error = "数据解析错误"
        } else if (e is ApiException) {
            error = convertServerCode(e)
        }
        return error
    }

    private fun convertHttpCode(httpException: HttpException): String {
        val msg: String
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg = "服务器处理请求出错"
        } else if (httpException.code() >= 400 && httpException.code() < 500) {
            msg = "服务器无法处理请求"
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg = "请求被重定向到其他页面"
        } else {
            msg = httpException.message()
        }
        return msg
    }

    private fun convertServerCode(apiException: ApiException): String {
        var msg = ""
        when (apiException.code) {
            "104" -> msg = "登录状态失效"

            "003" -> msg = "身份证过期"

            "005" -> msg = "需要短信验证码验证"

            else -> {
            }
        }
        return msg
    }
}
