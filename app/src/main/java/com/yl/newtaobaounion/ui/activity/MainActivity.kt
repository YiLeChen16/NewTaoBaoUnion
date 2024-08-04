package com.yl.newtaobaounion.ui.activity

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.base.BaseActivity
import com.yl.newtaobaounion.databinding.ActivityMainBinding
import com.yl.newtaobaounion.ui.adapter.FragmentAdapter
import com.yl.newtaobaounion.ui.fragment.GratiaFragment
import com.yl.newtaobaounion.ui.fragment.HomeFragment
import com.yl.newtaobaounion.ui.fragment.SearchFragment
import com.yl.newtaobaounion.ui.fragment.SelectedFragment
import com.yl.newtaobaounion.utils.LogUtils

//程序主入口
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var homeFragment: HomeFragment? = null
    private var selectedFragment: SelectedFragment? = null
    private var gratiaFragment: GratiaFragment? = null
    private var searchFragment: SearchFragment? = null

    private var fragmentAdapter: FragmentAdapter? = null

    override fun initView() {
        //初始化viewPager中的fragment
        initFragment()
        //禁用viewPager的滑动，避免和首页的viewpager滑动冲突
        binding.viewPager.isUserInputEnabled = false
    }

    private fun initFragment() {
        //创建四个item对应的fragment对象
        homeFragment = HomeFragment()
        selectedFragment = SelectedFragment()
        gratiaFragment = GratiaFragment()
        searchFragment = SearchFragment()
        //创建集合用于存储四个Fragment
        val list = arrayListOf<Fragment>(
            homeFragment!!,
            selectedFragment!!,
            gratiaFragment!!,
            searchFragment!!
        )
        //创建ViewPager的适配器
        fragmentAdapter = FragmentAdapter(this, list)
        //给ViewPager设置适配器
        binding.viewPager.adapter = fragmentAdapter
        binding.viewPager.currentItem = 0
    }

    override fun initListener() {
        //给底部导航栏设置监听
        //与viewPager绑定
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            LogUtils.d(this, "itemID->${item.itemId}")
            when (item.itemId) {
                R.id.home_item -> {
                    binding.viewPager.currentItem = 0
                    LogUtils.d(this, "home clicked")
                }

                R.id.selected_item -> {
                    binding.viewPager.currentItem = 1
                    LogUtils.d(this, "selected clicked")
                }

                R.id.gratia_item -> {
                    binding.viewPager.currentItem = 2
                    LogUtils.d(this, "gratia clicked")
                }

                R.id.search_item -> {
                    binding.viewPager.currentItem = 3
                    LogUtils.d(this, "search clicked")
                }
            }
            true
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 在页面被选中时调用
                when (position) {
                    0 -> binding.bottomNavigation.selectedItemId = R.id.home_item
                    1 -> binding.bottomNavigation.selectedItemId = R.id.selected_item
                    2 -> binding.bottomNavigation.selectedItemId = R.id.gratia_item
                    3 -> binding.bottomNavigation.selectedItemId = R.id.search_item
                }
            }
        })
    }

    override fun initPresenter() {

    }


    //定义方法给外界切换到SearchFragment
    fun switchToSearchFragment() {
        binding.viewPager.currentItem = 3

    }

}