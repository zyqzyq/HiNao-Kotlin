package com.zyqzyq.hinao.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.data.startBehavior
import com.zyqzyq.hinao.data.startMove
import com.zyqzyq.hinao.data.stopAllBehavior
import com.zyqzyq.hinao.ui.App
import com.zyqzyq.hinao.ui.activities.settings.MoveSetting
import kotlinx.android.synthetic.main.fragment_move.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity

class MoveFragment : Fragment() {
    private var mSharedPreferences : SharedPreferences? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_move, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbarTitle.text = getString(R.string.title_move)
        toolbar.inflateMenu(R.menu.toolbar_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nao_setting -> {
                    activity.startActivity<MoveSetting>()
                }
            }
            true
        }
        mSharedPreferences = activity.getSharedPreferences(MoveSetting.MoveSettingFragment.PREFER_NAME, Context.MODE_PRIVATE)
        quickBtn1.setOnClickListener { startBehavior(getString(R.string.quickBtn1)) }
        quickBtn2.setOnClickListener { startBehavior(getString(R.string.quickBtn2)) }
        quickBtn3.setOnClickListener { startBehavior(getString(R.string.quickBtn3)) }
        quickBtn4.setOnClickListener { startBehavior(getString(R.string.quickBtn4)) }
        quickBtn5.setOnClickListener { startBehavior(getString(R.string.quickBtn5)) }
        quickBtn6.setOnClickListener { startBehavior(getString(R.string.quickBtn6)) }
        quickBtn7.setOnClickListener { startBehavior(getString(R.string.quickBtn7)) }
        quickBtn8.setOnClickListener { startBehavior(getString(R.string.quickBtn8)) }
        quickBtn9.setOnClickListener { startBehavior(getString(R.string.quickBtn9)) }
        stopBtn.setOnClickListener {stopAllBehavior()}
        upBtn.setOnClickListener { startMove("01") }
        downBtn.setOnClickListener{ startMove("02")}
        leftBtn.setOnClickListener { startMove("03") }
        rightBtn.setOnClickListener { startMove("04") }
    }
    override fun onResume() {
        super.onResume()
        App.server_ip = mSharedPreferences?.getString("ip_preference","255.255.255.255")
        App.server_port = mSharedPreferences?.getString("port_preference","10024")?.toInt()

    }
}
