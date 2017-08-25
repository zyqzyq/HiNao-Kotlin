package com.zyqzyq.hinao.ui.activities.infomations

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.ToolbarManager
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class NaoInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nao_info)
        toolbarText.text = getString(R.string.infoText)
    }



}
