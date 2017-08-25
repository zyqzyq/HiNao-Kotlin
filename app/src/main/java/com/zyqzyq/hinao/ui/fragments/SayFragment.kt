package com.zyqzyq.hinao.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.Intent
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.data.startAnswer
import com.zyqzyq.hinao.data.startSay
import com.zyqzyq.hinao.ui.activities.NaoChatting
import com.zyqzyq.hinao.ui.activities.NaoSetting
import kotlinx.android.synthetic.main.fragment_say.*
import org.jetbrains.anko.startActivity


class SayFragment: Fragment() {
    private var mSharedPreferences : SharedPreferences? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_say,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSharedPreferences = activity.getSharedPreferences(NaoSetting.NaoSettingFragment.PREFER_NAME, Context.MODE_PRIVATE)
        robotSayBtn.setOnClickListener { startSay(robotSayText.text.toString()) }
        robotSayBtn1.setOnClickListener { startSay(robotSayBtn1.text.toString()) }
        robotSayBtn2.setOnClickListener { startSay(robotSayBtn2.text.toString()) }
        robotSayBtn3.setOnClickListener { startSay(robotSayBtn3.text.toString()) }

        robotAnswerBtn.setOnClickListener { startAnswer(robotAnswerText.text.toString()) }
        robotAnswerBtn1.setOnClickListener { startAnswer(robotAnswerBtn1.text.toString()) }
        robotAnswerBtn2.setOnClickListener { startAnswer(robotAnswerBtn2.text.toString()) }
        robotAnswerBtn3.setOnClickListener { startAnswer(robotAnswerBtn3.text.toString()) }

        startChatting.setOnClickListener { activity.startActivity<NaoChatting>() }
    }

    override fun onResume() {
        super.onResume()
        robotSayBtn1.text = mSharedPreferences?.getString("nao_say_1", "你好")
        robotSayBtn2.text = mSharedPreferences?.getString("nao_say_2", "今天天气不错")
        robotSayBtn3.text = mSharedPreferences?.getString("nao_say_3", "来和我聊天吧")

        robotAnswerBtn1.text = mSharedPreferences?.getString("nao_answer_1","你好，你叫什么名字")
        robotAnswerBtn2.text = mSharedPreferences?.getString("nao_answer_2","你多大了?")
        robotAnswerBtn3.text = mSharedPreferences?.getString("nao_answer_3","你来自哪里？")
    }
}