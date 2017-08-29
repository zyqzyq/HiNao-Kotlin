package com.zyqzyq.hinao.ui.activities.settings

import android.app.Activity
import android.os.Bundle
import android.preference.Preference
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceFragment
import com.zyqzyq.hinao.R


class MoveSetting: Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, MoveSettingFragment())
                .commit()
    }
    class MoveSettingFragment: PreferenceFragment(), OnPreferenceChangeListener {
        companion object {
            val PREFER_NAME = "com.zyqzyq.movesetting"
        }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            preferenceManager.sharedPreferencesName = PREFER_NAME
            addPreferencesFromResource(R.xml.move_setting)
        }
        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            return true
        }
    }
}