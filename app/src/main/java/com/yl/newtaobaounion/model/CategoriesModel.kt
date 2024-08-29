package com.yl.newtaobaounion.model

import com.yl.newtaobaounion.model.dataBean.CategoriesBean
import com.yl.newtaobaounion.repository.CategoriesRepository

class CategoriesModel {
    companion object {
        fun getCategories(
            successCallBack: (categoriesBean: CategoriesBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            CategoriesRepository.getCategoriesData(successCallBack,emptyCallback,errorCallBack)
        }
    }

}