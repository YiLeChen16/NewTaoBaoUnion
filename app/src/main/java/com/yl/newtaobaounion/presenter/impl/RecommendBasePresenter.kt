package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.model.RecommendBaseDataModel
import com.yl.newtaobaounion.presenter.IRecommendBasePresenter
import com.yl.newtaobaounion.view.IonBaseRecommendCallback

//Presenter层只负责视图逻辑，业务逻辑提升到Model层
class RecommendBasePresenter private constructor() : IRecommendBasePresenter {
    private var onBaseRecommendcallBack: IonBaseRecommendCallback? = null

    //提供获取此类对象的方法
    companion object {
        private var categoriesPresenter: RecommendBasePresenter = RecommendBasePresenter()
        fun getInstance(): RecommendBasePresenter {
            return categoriesPresenter
        }
    }

    /**
     * 请求分类数据，通知对应View层状态
     */
    override fun getCategories() {
        //通知View层，数据加载中
        onBaseRecommendcallBack?.onLoading()
        //通过Model层来请求数据
        RecommendBaseDataModel.getCategories(
            //数据请求成功回调
            successCallBack = { categoriesBean ->
                //通知View层，加载数据刷新成功界面
                onBaseRecommendcallBack?.onCategoriesDataLoad(categoriesBean)
            },
            emptyCallback = {
                //通知View层，加载数据为空界面
                onBaseRecommendcallBack?.onEmpty()
            },
            errorCallBack = {
                //通知View层，加载数据加载错误界面
                onBaseRecommendcallBack?.onError()
            }
        )
    }

    /**
     * 请求轮播图数据，通知View层状态
     */
    override fun getBannerData() {
        //通过Model层来请求数据
        RecommendBaseDataModel.getBannerData("3",
            successCallBack = { bannerBean ->
                //通知View层，加载数据刷新成功界面
                onBaseRecommendcallBack?.onBannerDataLoad(bannerBean)
            },
            emptyCallBack = {
                //通知View层，加载数据为空界面
                onBaseRecommendcallBack?.onBannerDataNullOrEmpty()
            },
            errorCallBack = {
                //通知View层，加载数据加载错误界面
                onBaseRecommendcallBack?.onBannerDataNullOrEmpty()
            })
    }

    override fun registerViewCallback(callback: IonBaseRecommendCallback) {
        this.onBaseRecommendcallBack = callback
    }

    override fun unregisterViewCallback(callback: IonBaseRecommendCallback) {
        this.onBaseRecommendcallBack = null
    }

}