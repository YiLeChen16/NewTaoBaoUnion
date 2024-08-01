package com.yl.newtaobaounion.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yl.newtaobaounion.utils.LogUtils

class FragmentAdapter(activity: FragmentActivity,private var fragmentList:List<Fragment>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        LogUtils.d(this,"getItemCount->${fragmentList.size}")
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        LogUtils.d(this,"getItemCount->${fragmentList[position]}")
        return fragmentList[position]
    }
}