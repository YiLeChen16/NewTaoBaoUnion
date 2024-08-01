package com.yl.newtaobaounion.ui.fragment

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.yl.newtaobaounion.base.BaseFragment
import com.yl.newtaobaounion.databinding.FragmentHomeBaseBinding
import com.yl.newtaobaounion.databinding.FragmentHomeBinding
import com.yl.newtaobaounion.moudle.CategoriesBean
import com.yl.newtaobaounion.presenter.impl.CategoriesPresenter
import com.yl.newtaobaounion.ui.adapter.HomeViewPagerAdapter
import com.yl.newtaobaounion.utils.LogUtils
import com.yl.newtaobaounion.utils.PresenterManager
import com.yl.newtaobaounion.view.IonCategoriesDataCallback


class HomeFragment : BaseFragment(), IonCategoriesDataCallback {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var categoriesPresenter:CategoriesPresenter
    override fun loadData() {

    }

    override fun loadRootViewBinding(container: ViewGroup?): ViewBinding {
        return FragmentHomeBaseBinding.inflate(layoutInflater)
    }

    override fun loadViewBinding(): FragmentHomeBinding {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

    }

    override fun initPresenter() {
        categoriesPresenter = PresenterManager.getInstance().categoriesPresenter
        //注册分类数据回调接口
        categoriesPresenter.registerViewCallback(this)
        categoriesPresenter.getCategories()
    }

    override fun initListener() {

    }

    override fun retry() {

    }


    override fun onCategoriesDataLoad(categoriesBean: CategoriesBean?) {
        val categories = categoriesBean?.data
        LogUtils.d(this, "onCategoriesDataLoad")
        LogUtils.d(this, "categoriesBean-->$categoriesBean")
        //将数据设置到tablayout中
        //为ViewPager设置适配器
        if (categories != null) {
            LogUtils.d(this, "categories != null")
            LogUtils.d(this, "requireActivity()-->${requireActivity()}")
            //binding = FragmentHomeBinding.inflate(layoutInflater)
            homeViewPagerAdapter = HomeViewPagerAdapter(this)
            homeViewPagerAdapter.setData(categories)
            binding.homeViewPager.adapter = homeViewPagerAdapter

            TabLayoutMediator(binding.tabLayout, binding.homeViewPager, true) { tab, position ->
                tab.text = categories[position].word
                LogUtils.d(this@HomeFragment,"tab.text-->${tab.text}")
            }.attach()

            setupCurrentState(State.SUCCESS)
        }
    }


    override fun onError() {
        setupCurrentState(State.ERROR)

    }

    override fun onEmpty() {
        setupCurrentState(State.EMPTY)
    }

    override fun onLoading() {
        setupCurrentState(State.LOADING)
    }

    override fun release() {
        //取消注册
        categoriesPresenter.unregisterViewCallback(this)
    }
}