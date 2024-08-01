package com.yl.newtaobaounion.base

import javax.security.auth.callback.Callback

/**
 * Presenter基本接口
 */
//因为每个Presenter接口都需要有设置view层回调接口的方法，故将其抽成一个底层接口
//由于每个presenter对应的view层回调接口都不同，故此处使用泛型
interface IBasePresenter<T> {
    /**
     * 注册view层监听回调接口
     */
    fun registerViewCallback(callback:T)

    /**
     * 注销view层监听接口，防止内存泄漏
     */
    fun unregisterViewCallback(callback:T)
}