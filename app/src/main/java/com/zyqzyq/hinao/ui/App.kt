package com.zyqzyq.hinao.ui

import android.app.Application
import android.util.Log
import com.iflytek.cloud.SpeechUtility
import com.zyqzyq.hinao.R
import kotlin.properties.Delegates

class App: Application(){
    companion object {
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        SpeechUtility.createUtility(this@App, "appid=" + getString(R.string.app_id))
        Log.d("UDP","create")
        instance = this

    }
}