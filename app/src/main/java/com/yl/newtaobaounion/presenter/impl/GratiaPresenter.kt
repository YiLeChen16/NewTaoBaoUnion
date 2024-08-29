package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.model.GratiaModel
import com.yl.newtaobaounion.presenter.IGratiaPresenter
import com.yl.newtaobaounion.view.IGratiaDataCallback

class GratiaPresenter private constructor() : IGratiaPresenter {
    //提供获取此类对象的方法
    companion object {
        private var gratiaPresenter: GratiaPresenter = GratiaPresenter()
        fun getInstance(): GratiaPresenter {
            return gratiaPresenter
        }
    }

    private var callback: IGratiaDataCallback? = null
    override fun getGratia() {
        //通知View层，数据正在加载中
        callback?.onLoading()
        GratiaModel.getGratia(
            //通知View层，数据请求成功
            successCallback = { gratiaBean ->
                callback?.onGratiaDataLoad(gratiaBean)
            },
            //通知View层，数据为空
            emptyCallback = {
                callback?.onEmpty()
            },
            //通知View层，数据请求失败
            errorCallback = {
                callback?.onError()
            }
        )
    }

    override fun registerViewCallback(callback: IGratiaDataCallback) {
        this.callback = callback
    }

    override fun unregisterViewCallback(callback: IGratiaDataCallback) {
        this.callback = null
    }
}