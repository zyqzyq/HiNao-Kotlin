package com.zyqzyq.hinao.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.zyqzyq.hinao.ui.fragments.*

class ViewPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    //由于页面已经固定,故这里把Adapter需要的fragment提前创建
    private val mFragments = arrayOf(MoveFragment(), SayFragment(), BehaviorListFragment(), MyFragment())

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }
}