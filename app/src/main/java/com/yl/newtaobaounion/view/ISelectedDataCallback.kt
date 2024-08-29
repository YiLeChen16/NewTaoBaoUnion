package com.yl.newtaobaounion.view

import com.yl.newtaobaounion.base.IBaseViewCallback
import com.yl.newtaobaounion.model.dataBean.SelectedBean

interface ISelectedDataCallback:IBaseViewCallback {

    //首次数据加载成功
    fun onSelectedDataLoad(selectedBean: SelectedBean)

    //加载更多数据成功
    fun onMoreSelectedDataLoad(selectedBean: SelectedBean)

    //更多数据加载为空
    fun onMoreSelectedDataEmpty()

    //更多数据加载失败
    fun onMoreSelectedDataError()

}