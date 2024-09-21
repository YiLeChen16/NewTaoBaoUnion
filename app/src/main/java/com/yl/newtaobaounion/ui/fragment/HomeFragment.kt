package com.yl.newtaobaounion.ui.fragment

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.base.BaseFragment
import com.yl.newtaobaounion.databinding.FragmentHomeBaseBinding
import com.yl.newtaobaounion.databinding.FragmentHomeBinding
import com.yl.newtaobaounion.model.dataBean.BannerBean
import com.yl.newtaobaounion.model.dataBean.CategoriesBean
import com.yl.newtaobaounion.model.dataBean.Constant.ERROR_MSG
import com.yl.newtaobaounion.model.dataBean.Constant.NET_ERROR_MSG
import com.yl.newtaobaounion.presenter.impl.RecommendBasePresenter
import com.yl.newtaobaounion.ui.activity.MainActivity
import com.yl.newtaobaounion.ui.adapter.HomeViewPagerAdapter
import com.yl.newtaobaounion.ui.custom.BannerView
import com.yl.newtaobaounion.utils.LogUtils
import com.yl.newtaobaounion.utils.PresenterManager
import com.yl.newtaobaounion.utils.ToastUtils
import com.yl.newtaobaounion.view.IonBaseRecommendCallback


class HomeFragment : BaseFragment(), IonBaseRecommendCallback {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var baseBinding: FragmentHomeBaseBinding
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var recommendBasePresenter: RecommendBasePresenter
    private var mBannerView: BannerView? = null

    override fun loadData() {
        recommendBasePresenter.getCategories()
        recommendBasePresenter.getBannerData()
    }

    override fun loadRootViewBinding(container: ViewGroup?): ViewBinding {
        baseBinding = FragmentHomeBaseBinding.inflate(layoutInflater)
        return baseBinding
    }

    override fun loadViewBinding(): FragmentHomeBinding {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {
        //初始化自定义控件
        mBannerView = baseBinding.root.findViewById(R.id.banner_view)
        LogUtils.d(this,"$mBannerView")
    }

    override fun initPresenter() {
        recommendBasePresenter = PresenterManager.getInstance().categoriesPresenter
        //注册分类数据回调接口
        recommendBasePresenter.registerViewCallback(this)
    }

    override fun initListener() {
        //为搜索框设置点击跳转事件
        baseBinding.edSearchBox.setOnClickListener {
            LogUtils.d(this@HomeFragment, "edSearchBox is click")
            //切换到SearchFragment
            if (activity is MainActivity) {
                (activity as MainActivity).switchToSearchFragment()
            }
        }
    }

    override fun retry() {
        recommendBasePresenter.getCategories()
        recommendBasePresenter.getBannerData()
    }


    override fun onCategoriesDataLoad(categoriesBean: CategoriesBean?) {
        val categories = categoriesBean?.data
        LogUtils.d(this, "onCategoriesDataLoad")
        LogUtils.d(this, "categoriesBean-->$categoriesBean")
        //将数据设置到tabLayout中
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
                LogUtils.d(this@HomeFragment, "tab.text-->${tab.text}")
            }.attach()

            setupCurrentState(State.SUCCESS)
        }
    }


    /**
     * 轮播图数据请求成功回调此方法
     * @param bannerBean BannerBean
     */
    override fun onBannerDataLoad(bannerBean: BannerBean) {
        LogUtils.d(this, "onBannerDataLoad-->$bannerBean")
        LogUtils.d(this, "mBannerView-->$mBannerView")
        //将Banner设置为可见
        mBannerView?.visibility = View.VISIBLE
        //banner设置数据
        mBannerView?.setData(bannerBean.data.toMutableList())
    }

    /**
     * 轮播图数据请求为空或失败回调此方法
     */
    override fun onBannerDataNullOrEmpty() {
        //隐藏轮播图控件
        mBannerView?.visibility = View.GONE
    }


    override fun onError() {
        setupCurrentState(State.ERROR)
        ToastUtils.showToast(NET_ERROR_MSG)
    }

    override fun onEmpty() {
        setupCurrentState(State.EMPTY)
        ToastUtils.showToast(ERROR_MSG)
    }

    override fun onLoading() {
        setupCurrentState(State.LOADING)
    }

    override fun release() {
        //取消注册
        recommendBasePresenter.unregisterViewCallback(this)
    }
}