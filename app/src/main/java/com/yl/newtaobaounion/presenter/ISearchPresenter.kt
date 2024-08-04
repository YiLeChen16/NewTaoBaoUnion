package com.yl.newtaobaounion.presenter

import com.yl.newtaobaounion.base.IBasePresenter
import com.yl.newtaobaounion.view.ISearchDataCallback

interface ISearchPresenter:IBasePresenter<ISearchDataCallback> {
    /**
     * 获取搜索推荐热词
     */
    fun getHotkey()

    /**
     * 获取搜索关键词的数据
     */
    fun getSearchData(keyword:String)


    /**
     * 加载历史搜索记录
     */
    fun loadHistoryWord()

    /**
     * 删除历史记录
     */
    fun deleteHistory()

    /**
     * 加载更多数据
     */
    fun onLoadMoreData(keyword: String)

}