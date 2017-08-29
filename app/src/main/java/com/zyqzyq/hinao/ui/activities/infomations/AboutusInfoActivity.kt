package com.zyqzyq.hinao.ui.activities.infomations

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.ToolbarManager
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.jetbrains.anko.find

class AboutusInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aboutus_info)
        toolbarTitle.text = getString(R.string.aboutText)
    }
}
