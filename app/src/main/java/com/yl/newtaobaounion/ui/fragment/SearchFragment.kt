package com.yl.newtaobaounion.ui.fragment

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.yl.newtaobaounion.base.BaseFragment
import com.yl.newtaobaounion.databinding.FragmentSearchBaseBinding
import com.yl.newtaobaounion.databinding.FragmentSearchBinding
import com.yl.newtaobaounion.model.dataBean.HotKeyBean
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.presenter.impl.SearchPresenter
import com.yl.newtaobaounion.ui.adapter.DetailTagListAdapter
import com.yl.newtaobaounion.ui.custom.FlowTextLayout
import com.yl.newtaobaounion.utils.LogUtils
import com.yl.newtaobaounion.utils.PresenterManager
import com.yl.newtaobaounion.utils.ToastUtils
import com.yl.newtaobaounion.view.ISearchDataCallback

class SearchFragment:BaseFragment(), ISearchDataCallback, FlowTextLayout.OnItemClickListener {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var baseBinding:FragmentSearchBaseBinding
    private lateinit var searchPresenter:SearchPresenter
    private  var listAdapter:DetailTagListAdapter? = null


    override fun loadData() {
        //加载推荐词和历史搜索记录数据
        searchPresenter.getHotkey()
        //从SP中获取搜索记录数据
        searchPresenter.loadHistoryWord()
    }

    override fun loadRootViewBinding(container: ViewGroup?): ViewBinding {
        baseBinding = FragmentSearchBaseBinding.inflate(layoutInflater)
        return baseBinding
    }

    override fun loadViewBinding(): FragmentSearchBinding {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {
        //创建列表适配器
        listAdapter = DetailTagListAdapter(requireContext())
        binding.searchResultIst.layoutManager = StaggeredGridLayoutManager(2,1)
        binding.searchResultIst.adapter = listAdapter
    }

    override fun initPresenter() {
        searchPresenter = PresenterManager.getInstance().searchPresenter
        searchPresenter.registerViewCallback(this)
    }

    override fun initListener() {
        //给历史记录列表和推荐词列表设置点击关键字回传监听
        binding.flowTextLayoutHistories.setOnItemClickListener(this)
        binding.flowTextLayoutRecommend.setOnItemClickListener(this)
        //给搜索框设置监听事件
        baseBinding.tvSearchOrCancelSearch.setOnClickListener {
            if(baseBinding.tvSearchOrCancelSearch.text.toString().equals("搜索")){
                search(baseBinding.edSearchBox.text.toString())
            }else{
                cancelSearch()
            }
        }
        //点击删除全部搜索记录
        binding.ivDelete.setOnClickListener{
            searchPresenter.deleteHistory()//删除历史记录
            searchPresenter.loadHistoryWord()//刷新ui
        }
        //设置上拉刷新
        binding.refreshLayout.setOnLoadMoreListener {
            searchPresenter.onLoadMoreData(baseBinding.edSearchBox.text.toString())
        }
    }

    override fun retry() {
        //判断搜索框是否有内容，有则继续搜索
        if(!TextUtils.isEmpty(baseBinding.edSearchBox.text)){
            searchPresenter.getSearchData(baseBinding.edSearchBox.text.toString())
        }else{
            //加载推荐词和历史搜索记录数据
            searchPresenter.getHotkey()
            //从SP中获取搜索记录数据
            searchPresenter.loadHistoryWord()
        }
    }

    override fun release() {
        searchPresenter.unregisterViewCallback(this)
    }

    override fun onHotKeyLoad(hotKeyBean: HotKeyBean) {
        setupCurrentState(State.SUCCESS)
        val words = mutableListOf<String>()
        for (hotKeyData in hotKeyBean.data) {
            words.add(hotKeyData.word)
        }
        LogUtils.d(this,"words-->$words")
        binding.flowTextLayoutRecommend.setData(words)
    }

    override fun onSearchDataLoad(recommendBean: RecommendBean) {
        setupCurrentState(State.SUCCESS)
        listAdapter?.setData(recommendBean.data.list)
    }

    override fun onHistoryWordLoad(data: List<String>) {
        //setupCurrentState(State.SUCCESS)
        binding.flowTextLayoutHistories.removeAllViews()
        binding.flowTextLayoutHistories.setData(data)
    }

    override fun onHistoryEmpty() {
        binding.flowTextLayoutHistories.removeAllViews()
    }

    override fun onLoadMoreSuccess(recommendBean: RecommendBean) {
        //停止刷新加载
        binding.refreshLayout.finishLoadMore()
        ToastUtils.showToast("已成功加载${recommendBean.data.list.size}个宝贝~")
        //设置数据
        listAdapter?.addData(recommendBean.data.list)
    }

    override fun onLoadMoreEmpty() {
        //停止刷新加载
        binding.refreshLayout.finishLoadMore()
        ToastUtils.showToast("已到达宇宙的尽头~")
    }

    override fun onLoadMoreError() {
        //停止刷新加载
        binding.refreshLayout.finishLoadMore()
        ToastUtils.showToast("数据好像被外星人抢走咯~请稍后再试~")
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

    //推荐或历史搜索记录关键词被点击后，关键字从此方法回传过来
    override fun onItemClick(keyword: String) {
        //给搜索框设置数据
        baseBinding.edSearchBox.setText(keyword)
        search(keyword)
    }

    //搜索
    private fun search(s:String){
        if(s == ""){
            ToastUtils.showToast("搜索内容为空哦~")
            return
        }
        baseBinding.tvSearchOrCancelSearch.text = "取消"
        searchPresenter.getSearchData(s)
        binding.refreshLayout.visibility = View.VISIBLE
        binding.recommendLayout.visibility = View.INVISIBLE
        binding.searchHistoriesLayout.visibility = View.INVISIBLE
        //隐藏键盘
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(baseBinding.edSearchBox.windowToken, 0)
    }

    //取消搜索
    private fun cancelSearch(){
        baseBinding.edSearchBox.setText("")
        baseBinding.tvSearchOrCancelSearch.text = "搜索"
        searchPresenter.loadHistoryWord()//更新搜索历史记录
        //searchPresenter.getHotkey()
        binding.refreshLayout.visibility = View.GONE
        //切换为成功界面
        binding.recommendLayout.visibility = View.VISIBLE
        binding.searchHistoriesLayout.visibility = View.VISIBLE
    }
}