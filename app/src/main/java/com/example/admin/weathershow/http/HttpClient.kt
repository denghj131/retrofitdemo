package com.example.admin.weathershow.http

import com.example.admin.weathershow.config.HttpConfig
import com.example.admin.weathershow.interceptor.LogInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * OkHttp封装
 */
class HttpClient private constructor() {
    val builder: OkHttpClient.Builder

    init {
        builder = OkHttpClient.Builder()
        builder.connectTimeout(HttpConfig.HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(HttpConfig.HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(HttpConfig.HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)
            //                .addNetworkInterceptor(new TokenInterceptor(null))  //添加Token拦截器
            .addInterceptor(LogInterceptor())
    }

    companion object {
        private var mHttpClient: HttpClient? = null

        //双重检验锁单例模式
        val instance: HttpClient
            get() {
                if (mHttpClient == null) {
                    synchronized(HttpClient::class.java) {
                        if (mHttpClient == null) {
                            mHttpClient = HttpClient()
                        }
                    }
                }
                return this!!.mHttpClient!!
            }
    }
}
