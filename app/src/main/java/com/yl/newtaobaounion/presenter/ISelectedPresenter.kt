package com.yl.newtaobaounion.presenter

import com.yl.newtaobaounion.base.IBasePresenter
import com.yl.newtaobaounion.view.ISelectedDataCallback
//精选界面presenter接口
interface ISelectedPresenter:IBasePresenter<ISelectedDataCallback> {
    //获取猜你喜欢数据
    fun getSelectedData()

    //获取更多数据
    fun getMoreSelectedData()
}