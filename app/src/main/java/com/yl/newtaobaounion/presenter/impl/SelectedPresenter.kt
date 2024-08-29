package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.model.SelectedModel
import com.yl.newtaobaounion.presenter.ISelectedPresenter
import com.yl.newtaobaounion.view.ISelectedDataCallback

class SelectedPresenter private constructor() : ISelectedPresenter {
    //提供获取此类对象的方法
    companion object {
        private var selectedPresenter: SelectedPresenter = SelectedPresenter()
        fun getInstance(): SelectedPresenter {
            return selectedPresenter
        }
    }

    private var callback: ISelectedDataCallback? = null


    override fun getSelectedData() {
        //通知View层，数据正在加载中
        callback?.onLoading()
        SelectedModel.getSelected(
            //通知View层，数据请求成功
            successCallBack = { selectedBean ->
                callback?.onSelectedDataLoad(selectedBean)
            },
            //通知View层，数据为空
            emptyCallBack = {
                callback?.onEmpty()
            },
            //通知View层，数据请求失败
            errorCallBack = {
                callback?.onError()
            }
        )
    }

    //加载更多数据
    override fun getMoreSelectedData() {
        SelectedModel.getMoreSelected(
            //通知View层，数据请求成功
            successCallBack = { moreSelectedBean ->
                callback?.onMoreSelectedDataLoad(moreSelectedBean)
            },
            //通知View层，数据为空
            emptyCallBack = {
                callback?.onMoreSelectedDataEmpty()
            },
            //通知View层，数据加载失败
            errorCallBack = {
                callback?.onMoreSelectedDataError()
            }
        )
    }

    override fun registerViewCallback(callback: ISelectedDataCallback) {
        this.callback = callback
    }

    override fun unregisterViewCallback(callback: ISelectedDataCallback) {
        this.callback = null
    }
}