package com.yl.newtaobaounion.utils

import com.bumptech.glide.Glide.init
import com.yl.newtaobaounion.presenter.impl.CategoriesPresenter
import com.yl.newtaobaounion.presenter.impl.RecommendPresenter

//用于获取对应Presenter的单例
class PresenterManager private constructor() {
    companion object{
        //创建此类的对象
        private val presenterManager:PresenterManager = PresenterManager()
        //获取此类的单例
        fun getInstance():PresenterManager{
            return presenterManager
        }
    }
    //声明所有presenter的对象
    var categoriesPresenter:CategoriesPresenter
    var recommendPresenter:RecommendPresenter

    //在创建此类的对象时，初始化所有的presenter
    init {
        categoriesPresenter = CategoriesPresenter()
        recommendPresenter = RecommendPresenter.getInstance()
    }



}