package com.yl.newtaobaounion.presenter

import com.yl.newtaobaounion.base.IBasePresenter
import com.yl.newtaobaounion.view.IonCategoriesDataCallback

//用于请求分类数据
interface ICategoriesPresenter:IBasePresenter<IonCategoriesDataCallback> {
    //获取分类数据
    fun  getCategories()
}