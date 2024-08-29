package com.yl.newtaobaounion.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.yl.newtaobaounion.base.BaseFragment
import com.yl.newtaobaounion.databinding.FragmentGratiaBaseBinding
import com.yl.newtaobaounion.databinding.FragmentGratiaBinding
import com.yl.newtaobaounion.model.dataBean.GratiaBean
import com.yl.newtaobaounion.presenter.impl.GratiaPresenter
import com.yl.newtaobaounion.ui.adapter.GratiaListAdapter
import com.yl.newtaobaounion.utils.PresenterManager
import com.yl.newtaobaounion.view.IGratiaDataCallback

class GratiaFragment:BaseFragment(), IGratiaDataCallback {
    private lateinit var binding:FragmentGratiaBinding
    private lateinit var gratiaPresenter:GratiaPresenter


    override fun loadData() {
        //加载数据
        gratiaPresenter.getGratia()
    }

    override fun loadRootViewBinding(container: ViewGroup?): ViewBinding {
        return FragmentGratiaBaseBinding.inflate(layoutInflater)
    }

    override fun loadViewBinding(): FragmentGratiaBinding {
        binding = FragmentGratiaBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

    }

    override fun initPresenter() {
        gratiaPresenter = PresenterManager.getInstance().gratiaPresenter
        gratiaPresenter.registerViewCallback(this)
    }

    override fun initListener() {
    }

    override fun retry() {
        gratiaPresenter.getGratia()
    }


    override fun release() {
        gratiaPresenter.unregisterViewCallback(this)
    }

    override fun onGratiaDataLoad(gratiaBean: GratiaBean) {
        //TODO::
        //创建适配器
        val gratiaListAdapter = GratiaListAdapter()
        //为适配器设置数据
        gratiaListAdapter.setData(gratiaBean.data)
        binding.contentList.layoutManager = LinearLayoutManager(context)
        binding.contentList.adapter = gratiaListAdapter
        setupCurrentState(State.SUCCESS)
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
}