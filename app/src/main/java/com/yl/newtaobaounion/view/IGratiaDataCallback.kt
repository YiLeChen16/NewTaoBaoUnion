package com.yl.newtaobaounion.view

import com.yl.newtaobaounion.base.IBaseViewCallback
import com.yl.newtaobaounion.moudle.GratiaBean

interface IGratiaDataCallback:IBaseViewCallback {

    //特惠数据加载成功
    fun onGratiaDataLoad(gratiaBean: GratiaBean)
}