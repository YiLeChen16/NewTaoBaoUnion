package com.yl.newtaobaounion.presenter

import com.yl.newtaobaounion.base.IBasePresenter
import com.yl.newtaobaounion.view.IRecommendDataCallback

interface IRecommendPresenter : IBasePresenter<IRecommendDataCallback> {
    /**
     * 根据关键词，获取推荐数据
     * @param viewKeyword 页面关键字
     * @param keyword 页面关键字的细分标签
     * @param refresh 是否为刷新的数据
     */
    fun getRecommendDataByKeyWord(viewKeyword: String, keyword: String,refresh:Boolean)

    /**
     * 根据关键词，加载更多数据（加载下一页数据）
     */
    fun loadMoreRecommendDataByKeyWord(viewKeyword: String,keyword: String)

    /**
     * 根据关键词刷新当前页面（重写加载页面）
     *
     */
    fun reLoadRecommendDataByKeyWord(viewKeyword: String,keyword: String)
}