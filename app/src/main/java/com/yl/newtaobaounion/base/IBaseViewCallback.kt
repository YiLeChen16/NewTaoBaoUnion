package com.yl.newtaobaounion.base
//view类数据回调基接口
//此处只定义三种方法，不需要定义成功的方法，因为每个接口请求的数据都是不同的
interface IBaseViewCallback {
    /**
     * 请求错误
     */
    fun onError()

    /**
     * 请求内容为空
     */
    fun onEmpty()

    /**
     * 数据加载中
     */
    fun onLoading()
}