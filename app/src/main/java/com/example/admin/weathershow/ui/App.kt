package com.example.admin.weathershow.ui

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import kotlin.properties.Delegates

class App : Application() {
    companion object {

    }

    override fun onCreate() {
        super.onCreate()
        initUtils()
    }

    private fun initUtils() {
        Utils.init(this)

        val config = LogUtils.getConfig()
        config.setLogSwitch(true)
        config.setGlobalTag("tag")
    }
}