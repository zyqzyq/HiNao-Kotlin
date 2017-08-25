package com.zyqzyq.hinao.ui.activities

import android.app.Activity
import android.os.Bundle
import android.preference.Preference
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceFragment
import com.zyqzyq.hinao.R


class NaoSetting: Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content,NaoSettingFragment())
                .commit()
    }
    class NaoSettingFragment: PreferenceFragment(), OnPreferenceChangeListener {
        companion object {
            val PREFER_NAME = "com.zyqzyq.setting"
        }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            preferenceManager.sharedPreferencesName = PREFER_NAME
            addPreferencesFromResource(R.xml.nao_setting)
        }
        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            return true
        }
    }
}