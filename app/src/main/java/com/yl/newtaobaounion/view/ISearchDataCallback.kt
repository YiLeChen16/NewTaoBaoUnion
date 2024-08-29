package com.yl.newtaobaounion.view

import com.yl.newtaobaounion.base.IBaseViewCallback
import com.yl.newtaobaounion.model.dataBean.HotKeyBean
import com.yl.newtaobaounion.model.dataBean.RecommendBean

interface ISearchDataCallback:IBaseViewCallback {

    /**
     * 推荐热词加载成功 数据回传方法
     * @param hotKeyBean HotKeyBean
     */
    fun onHotKeyLoad(hotKeyBean: HotKeyBean)

    /**
     * 搜索成功 数据回传方法
     * @param recommendBean RecommendBean
     */
    fun onSearchDataLoad(recommendBean: RecommendBean)

    /**
     * 历史数据加载成功
     * @param data List<String>
     */
    fun onHistoryWordLoad(data : List<String>)

    /**
     * 历史数据为空
     */
    fun onHistoryEmpty()


    /**
     * 加载更多数据成功
     */
    fun onLoadMoreSuccess(recommendBean: RecommendBean)

    /**
     * 加载更多数据为空
     */
    fun onLoadMoreEmpty()

    /**
     * 加载更多数据错误
     */
    fun onLoadMoreError()
}