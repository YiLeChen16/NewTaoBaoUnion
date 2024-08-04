package com.yl.newtaobaounion.utils

import com.bumptech.glide.Glide.init
import com.yl.newtaobaounion.presenter.impl.CategoriesPresenter
import com.yl.newtaobaounion.presenter.impl.GratiaPresenter
import com.yl.newtaobaounion.presenter.impl.RecommendPresenter
import com.yl.newtaobaounion.presenter.impl.SearchPresenter
import com.yl.newtaobaounion.presenter.impl.SelectedPresenter

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
    var selectedPresenter:SelectedPresenter
    var gratiaPresenter:GratiaPresenter
    var searchPresenter:SearchPresenter

    //在创建此类的对象时，初始化所有的presenter
    init {
        categoriesPresenter = CategoriesPresenter.getInstance()
        recommendPresenter = RecommendPresenter.getInstance()
        selectedPresenter = SelectedPresenter.getInstance()
        gratiaPresenter = GratiaPresenter.getInstance()
        searchPresenter = SearchPresenter.getInstance()
    }



}