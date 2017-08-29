package com.zyqzyq.hinao.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.data.startSay
import com.zyqzyq.hinao.ui.activities.NaoChatting
import com.zyqzyq.hinao.ui.activities.settings.SaySetting
import kotlinx.android.synthetic.main.fragment_say.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity


class SayFragment: Fragment() {
    private var mSharedPreferences : SharedPreferences? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_say,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbarTitle.text = getString(R.string.title_say)
        toolbar.inflateMenu(R.menu.toolbar_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nao_setting -> {
                    activity.startActivity<SaySetting>()
                }
            }
            true
        }
        mSharedPreferences = activity.getSharedPreferences(SaySetting.SaySettingFragment.PREFER_NAME, Context.MODE_PRIVATE)
        robotSayBtn.setOnClickListener { startSay(robotSayText.text.toString()) }
        robotSayBtn1.setOnClickListener { startSay(robotSayBtn1.text.toString()) }
        robotSayBtn2.setOnClickListener { startSay(robotSayBtn2.text.toString()) }
        robotSayBtn3.setOnClickListener { startSay(robotSayBtn3.text.toString()) }
        robotSayBtn4.setOnClickListener { startSay(robotSayBtn4.text.toString()) }
        robotSayBtn5.setOnClickListener { startSay(robotSayBtn5.text.toString()) }
        robotSayBtn6.setOnClickListener { startSay(robotSayBtn6.text.toString()) }

        startChatting.setOnClickListener { activity.startActivity<NaoChatting>() }
    }

    override fun onResume() {
        super.onResume()
        robotSayBtn1.text = mSharedPreferences?.getString("nao_say_1", "你好")
        robotSayBtn2.text = mSharedPreferences?.getString("nao_say_2", "今天天气不错")
        robotSayBtn3.text = mSharedPreferences?.getString("nao_say_3", "来和我聊天吧")
        robotSayBtn4.text = mSharedPreferences?.getString("nao_say_4", "你好")
        robotSayBtn5.text = mSharedPreferences?.getString("nao_say_5", "今天天气不错")
        robotSayBtn6.text = mSharedPreferences?.getString("nao_say_6", "来和我聊天吧")
    }
}