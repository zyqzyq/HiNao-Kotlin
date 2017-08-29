package com.zyqzyq.hinao.ui

import android.app.Application
import android.content.Context
import android.util.Log
import com.iflytek.cloud.SpeechUtility
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.ui.activities.settings.MoveSetting
import kotlin.properties.Delegates

class App: Application(){


    companion object {
        var instance: App by Delegates.notNull()
        var server_ip: String? = "255.255.255.123"
        var server_port: Int? = 10023
    }

    override fun onCreate() {
        super.onCreate()
        SpeechUtility.createUtility(this@App, "appid=" + getString(R.string.app_id))
        Log.d("UDP","create")
        instance = this

    }
}