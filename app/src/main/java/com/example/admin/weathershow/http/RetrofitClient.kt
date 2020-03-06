package com.example.admin.weathershow.http

import com.example.admin.weathershow.config.HttpConfig
import com.example.admin.weathershow.data.apiservice.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 对Retrofit进行简单的封装
 */
class RetrofitClient {
    val api: Api

    init {
        val builder = HttpClient.instance.builder

        val retrofit = Retrofit.Builder()
            .baseUrl(HttpConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())          //添加Gson转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //添加RxJava转换器
            .client(builder.build())
            .build()

        api = retrofit.create(Api::class.java)
    }

    companion object {
        private var mRetrofitClient: RetrofitClient? = null

        val instance: RetrofitClient
            get() {
                if (mRetrofitClient == null) {
                    synchronized(RetrofitClient::class.java) {
                        if (mRetrofitClient == null)
                            mRetrofitClient = RetrofitClient()
                    }
                }
                return this!!.mRetrofitClient!!
            }
    }
}
