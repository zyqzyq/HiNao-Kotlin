package com.zyqzyq.hinao.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.ui.activities.infomations.AboutusInfoActivity
import com.zyqzyq.hinao.ui.activities.infomations.DownloadInfoActivity
import com.zyqzyq.hinao.ui.activities.infomations.NaoInfoActivity
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity


class MyFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_my,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        aboutusBtn.setOnClickListener { activity.startActivity<AboutusInfoActivity>() }
        naoInfoBtn.setOnClickListener { activity.startActivity<NaoInfoActivity>() }
        downloadBtn.setOnClickListener { activity.startActivity<DownloadInfoActivity>() }

        toolbarTitle.text = getString(R.string.title_my)

    }
}