package com.yl.newtaobaounion.view

import com.yl.newtaobaounion.base.IBaseViewCallback
import com.yl.newtaobaounion.model.dataBean.RecommendBean

//推荐数据view层回调接口
interface IRecommendDataCallback:IBaseViewCallback {
    /**
     * 数据加载成功后回调此方法
     */

    fun onRecommendDataLoad(recommendBean: RecommendBean)


    /**
     * 更多数据加载成功后回调此方法
     */
    fun onMoreDataLoadSuccess(recommendBean: RecommendBean)

    /**
     * 更多数据加载失败后回调此方法
     */
    fun onMoreDataLoadError()

    /**
     * 更多数据加载为空后回调此方法
     */
    fun onMoreDataLoadEmpty()

    /**
     * 刷新数据加载成功后回调此方法
     *
     */
    fun onRefreshDataLoadSuccess(recommendBean: RecommendBean)

    /**
     * 刷新数据加载失败后回调此方法
     *
     */
    fun onRefreshDataLoadError()

    /**
     * 刷新数据加载为空后回调此方法
     *
     */
    fun onRefreshDataLoadEmpty()



    /**
     * 返回当前view对应的关键字
     */
    fun getViewKeyWord():String
}