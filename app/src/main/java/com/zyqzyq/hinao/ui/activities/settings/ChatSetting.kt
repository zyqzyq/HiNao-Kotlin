package com.zyqzyq.hinao.ui.activities.settings

import android.app.Activity
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import com.zyqzyq.hinao.R

class ChatSetting : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, ChatSettingFragment())
                .commit()
    }
    class ChatSettingFragment: PreferenceFragment(), Preference.OnPreferenceChangeListener {
        companion object {
            val PREFER_NAME = "com.zyqzyq.chatsetting"
        }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            preferenceManager.sharedPreferencesName = PREFER_NAME
            addPreferencesFromResource(R.xml.chat_setting)
        }
        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            return true
        }
    }
}