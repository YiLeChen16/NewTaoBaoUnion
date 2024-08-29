package com.yl.newtaobaounion.view

import com.yl.newtaobaounion.base.IBaseViewCallback
import com.yl.newtaobaounion.model.dataBean.CategoriesBean

//定义分类数据回调接口
interface IonCategoriesDataCallback:IBaseViewCallback{
    /**
     * 分类数据加载成功
     */
    fun onCategoriesDataLoad(categoriesBean: CategoriesBean?)
}