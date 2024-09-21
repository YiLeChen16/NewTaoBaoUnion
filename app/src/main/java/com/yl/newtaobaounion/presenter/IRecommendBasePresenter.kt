package com.yl.newtaobaounion.presenter

import com.yl.newtaobaounion.base.IBasePresenter
import com.yl.newtaobaounion.view.IonBaseRecommendCallback

//用于请求推荐页的基础数据
interface IRecommendBasePresenter : IBasePresenter<IonBaseRecommendCallback> {
    //获取分类数据
    fun getCategories()

    //请求轮播图数据
    fun getBannerData()
}