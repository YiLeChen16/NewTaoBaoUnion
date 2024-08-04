package com.yl.newtaobaounion.presenter

import com.yl.newtaobaounion.base.IBasePresenter
import com.yl.newtaobaounion.view.IGratiaDataCallback

interface IGratiaPresenter:IBasePresenter<IGratiaDataCallback> {

    /**
     * 获取特惠数据
     */
    fun getGratia()
}