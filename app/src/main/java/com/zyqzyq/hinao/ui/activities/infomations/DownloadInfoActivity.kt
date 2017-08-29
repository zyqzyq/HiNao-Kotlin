package com.zyqzyq.hinao.ui.activities.infomations

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.ToolbarManager
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.find

class DownloadInfoActivity : AppCompatActivity() ,ToolbarManager{
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_info)
        toolbarTitle.text = getString(R.string.downloadText)
    }
}
