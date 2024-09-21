package com.yl.newtaobaounion.view

import com.yl.newtaobaounion.base.IBaseViewCallback
import com.yl.newtaobaounion.model.dataBean.BannerBean
import com.yl.newtaobaounion.model.dataBean.CategoriesBean

//定义推荐页基础数据回调接口
interface IonBaseRecommendCallback:IBaseViewCallback{
    /**
     * 分类数据加载成功
     */
    fun onCategoriesDataLoad(categoriesBean: CategoriesBean?)



    /**
     * 轮播图数据请求成功后回调此方法
     */
    fun onBannerDataLoad(bannerBean: BannerBean)

    /**
     * 轮播图数据请求为空或失败回调此方法
     *
     */
    fun onBannerDataNullOrEmpty()
}