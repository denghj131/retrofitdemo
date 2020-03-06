package com.example.admin.weathershow.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.BufferedSource
import java.io.IOException
import java.nio.charset.Charset

/**
 * 日志拦截器
 */
class LogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val source = response.body()!!.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()

        val log =
            "" + ("\nrequest code ====== " + response.code()) + ("\nrequest url ====== " + request.url()) + ("\nrequest duration ====== " + (response.receivedResponseAtMillis() - response.sentRequestAtMillis()) + "ms") + ("\nrequest header ====== " + request.headers()) + ("\nrequest body ====== " + bodyToString(
                request.body()
            )) + ("\nresponse body ====== " + buffer.clone().readString(UTF8))

        Log.e("tmd", "===请求信息$log")
        return response
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")

        /**
         * 请求体转String
         *
         * @param request 请求体
         * @return String 类型的请求体
         */
        private fun bodyToString(request: RequestBody?): String {
            try {
                val buffer = Buffer()
                request!!.writeTo(buffer)
                return buffer.readUtf8()
            } catch (e: Exception) {
                return "no request body"
            }

        }
    }
}
