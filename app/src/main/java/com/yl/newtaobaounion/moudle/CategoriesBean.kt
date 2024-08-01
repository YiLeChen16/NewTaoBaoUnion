package com.yl.newtaobaounion.moudle

data class CategoriesBean(
    val code: Int,
    val `data`: List<CategoriesData>,
    val msg: String,
    val success: Boolean
)

data class CategoriesData(
    val clickCount: Int,
    val color: String,
    val createTime: String,
    val enable: String,
    val icon: String,
    val id: String,
    val ord: Int,
    val parentId: String,
    val subCategoryList: List<SubCategory>,
    val updateTime: String,
    val word: String
)

data class SubCategory(
    val clickCount: Int,
    val color: String,
    val createTime: String,
    val enable: String,
    val icon: String,
    val id: String,
    val ord: Int,
    val parentId: String,
    val updateTime: String,
    val word: String
)

