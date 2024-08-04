package com.yl.newtaobaounion.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.yl.newtaobaounion.base.BaseFragment
import com.yl.newtaobaounion.databinding.FragmentSelectedBaseBinding
import com.yl.newtaobaounion.databinding.FragmentSelectedBinding
import com.yl.newtaobaounion.moudle.SelectedBean
import com.yl.newtaobaounion.presenter.impl.SelectedPresenter
import com.yl.newtaobaounion.ui.adapter.DetailTagListAdapter
import com.yl.newtaobaounion.utils.PresenterManager
import com.yl.newtaobaounion.utils.ToastUtils
import com.yl.newtaobaounion.view.ISelectedDataCallback

class SelectedFragment : BaseFragment(), ISelectedDataCallback {
    private lateinit var selectedPresenter: SelectedPresenter
    private lateinit var binding: FragmentSelectedBinding
    private lateinit var detailTagListAdapter: DetailTagListAdapter

    override fun loadData() {
        //加载数据
        selectedPresenter.getSelectedData()
    }

    override fun loadRootViewBinding(container: ViewGroup?): ViewBinding {
        return FragmentSelectedBaseBinding.inflate(layoutInflater)
    }

    override fun loadViewBinding(): FragmentSelectedBinding {
        binding = FragmentSelectedBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {
        binding.refreshLayout.setEnableRefresh(false) //暂时禁用下拉刷新功能

    }

    override fun initPresenter() {
        selectedPresenter = PresenterManager.getInstance().selectedPresenter
        //注册监听回调
        selectedPresenter.registerViewCallback(this)
    }

    override fun initListener() {
        //设置上拉刷新监听事件
        binding.refreshLayout.setOnLoadMoreListener {
            selectedPresenter.getMoreSelectedData()
        }
    }

    override fun retry() {
        selectedPresenter.getSelectedData()
    }

    override fun release() {
        //取消view层监听回调
        selectedPresenter.unregisterViewCallback(this)
    }

    //首次数据加载成功
    override fun onSelectedDataLoad(selectedBean: SelectedBean) {
        //创建适配器
        detailTagListAdapter = DetailTagListAdapter(requireContext())
        binding.contentList.layoutManager = StaggeredGridLayoutManager(2, 1)
        //给适配器设置数据
        detailTagListAdapter.setData(selectedBean.data.list)
        //装载数据
        binding.contentList.adapter = detailTagListAdapter
        setupCurrentState(State.SUCCESS)
    }

    //更多数据加载成功
    override fun onMoreSelectedDataLoad(selectedBean: SelectedBean) {
        //停止刷新加载
        binding.refreshLayout.finishLoadMore()
        ToastUtils.showToast("已成功加载${selectedBean.data.list.size}个宝贝~")
        //装载新增数据
        detailTagListAdapter.addData(selectedBean.data.list)
    }

    //更多数据加载为空
    override fun onMoreSelectedDataEmpty() {
        //停止刷新加载
        binding.refreshLayout.finishLoadMore()
        ToastUtils.showToast("已到达宇宙的尽头~")
    }

    //更多数据加载失败
    override fun onMoreSelectedDataError() {
        //停止刷新加载
        binding.refreshLayout.finishLoadMore()
        ToastUtils.showToast("数据好像被外星人抢走咯~请稍后再试~")
    }

    override fun onError() {
        setupCurrentState(State.ERROR)
        ToastUtils.showToast("断水断电别断网啊，请稍后再试~")
    }

    override fun onEmpty() {
        setupCurrentState(State.EMPTY)
        ToastUtils.showToast("数据好像被外星人抢走咯~请稍后再试~")
    }

    override fun onLoading() {
        setupCurrentState(State.LOADING)
    }

}