package com.zyqzyq.hinao

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.zyqzyq.hinao.ui.App
import org.jetbrains.anko.toast


interface ToolbarManager{
    val toolbar: Toolbar

    fun initToolBar(){
        toolbar.inflateMenu(R.menu.toolbar_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nao_setting -> {
                    App.instance.toast("setting")
                }
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }

    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }
    private fun createUpDrawable() = with (DrawerArrowDrawable(toolbar.context)){
        progress = 1f
        this
    }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx:
            Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }

    fun View.slideExit() {
        if (translationY == 0f) animate().translationY(-height.toFloat())
    }
    fun View.slideEnter() {
        if (translationY < 0f) animate().translationY(0f)
    }

}