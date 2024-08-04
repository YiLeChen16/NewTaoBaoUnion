package com.yl.newtaobaounion.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yl.newtaobaounion.moudle.CategoriesBean
import com.yl.newtaobaounion.moudle.CategoriesData
import com.yl.newtaobaounion.ui.fragment.HomeFragment
import com.yl.newtaobaounion.ui.fragment.HomeViewPagerFragment
import com.yl.newtaobaounion.utils.LogUtils

//主页TabLayout的ViewPager适配器
class HomeViewPagerAdapter(fragment:HomeFragment) :FragmentStateAdapter(fragment){
    private val fragmentList = mutableListOf<HomeViewPagerFragment>()

    override fun getItemCount(): Int {
        LogUtils.d(this,"getItemCount-->${fragmentList.size}")
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    //设置数据
    fun setData(categories: List<CategoriesData>){
        categories.forEachIndexed { index, it ->
            //根据请求的数据量，动态创建Fragment
            LogUtils.d(this,"setData-->$it")
            fragmentList.add(HomeViewPagerFragment.newInstance(it))
        }
        //刷新数据
        notifyItemRangeChanged(0,fragmentList.size)
    }
}