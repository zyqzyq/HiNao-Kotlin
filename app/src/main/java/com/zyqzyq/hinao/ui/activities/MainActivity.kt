package com.zyqzyq.hinao.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.ToolbarManager
import com.zyqzyq.hinao.ui.App
import com.zyqzyq.hinao.ui.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity: AppCompatActivity(),ViewPager.OnPageChangeListener, ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolBar()
        viewpager.addOnPageChangeListener(this)
        viewpager.adapter = ViewPagerAdapter(supportFragmentManager)
        bottomNavView.selectedItemId = R.id.bottom_move
        bottomNavView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottom_move -> {
                    toolbarText.text = item.title
                    viewpager.currentItem = 0
                }
                R.id.bottom_say -> {
                    toolbarText.text = item.title
                    viewpager.currentItem = 1
                }
                R.id.bottom_behaviorlist -> {
                    toolbarText.text = item.title
                    viewpager.currentItem = 2
                }
                R.id.bottom_other -> {
                    toolbarText.text = item.title
                    viewpager.currentItem = 3
                }
            }
            true
        })
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nao_setting -> {
                    App.instance.toast("setting")
                    startActivity<NaoSetting>()
                }
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }


        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            when(position){
                0-> bottomNavView.selectedItemId = R.id.bottom_move
                1-> bottomNavView.selectedItemId = R.id.bottom_say
                2-> bottomNavView.selectedItemId = R.id.bottom_behaviorlist
                3-> bottomNavView.selectedItemId = R.id.bottom_other
            }
        }


}

