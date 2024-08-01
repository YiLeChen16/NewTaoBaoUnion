package com.yl.newtaobaounion.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.base.BaseFragment
import com.yl.newtaobaounion.databinding.BaseLayoutBinding
import com.yl.newtaobaounion.databinding.FragmentSelectedBinding
import java.lang.reflect.ParameterizedType

class SelectedFragment:BaseFragment() {
    override fun loadData() {

    }

    override fun loadRootViewBinding(container: ViewGroup?): ViewBinding {
        return BaseLayoutBinding.inflate(layoutInflater)
    }

    override fun loadViewBinding(): FragmentSelectedBinding {
        return FragmentSelectedBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun initPresenter() {
    }

    override fun initListener() {
    }

    override fun retry() {
        TODO("Not yet implemented")
    }

    override fun release() {
    }
}