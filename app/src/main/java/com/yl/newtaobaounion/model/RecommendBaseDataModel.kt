package com.yl.newtaobaounion.model

import com.yl.newtaobaounion.model.dataBean.BannerBean
import com.yl.newtaobaounion.model.dataBean.CategoriesBean
import com.yl.newtaobaounion.repository.CategoriesRepository
import com.yl.newtaobaounion.repository.RecommendRepository

class RecommendBaseDataModel {
    companion object {
        fun getCategories(
            successCallBack: (categoriesBean: CategoriesBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            CategoriesRepository.getCategoriesData(successCallBack,emptyCallback,errorCallBack)
        }

        /**
         * 获取轮播图数据
         */
        fun getBannerData(
            count: String,
            successCallBack: (bannerBean: BannerBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            RecommendRepository.getBannerDataByCount(
                count,
                successCallBack,
                emptyCallBack,
                errorCallBack
            )
        }
    }

}