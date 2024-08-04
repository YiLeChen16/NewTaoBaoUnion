package com.yl.newtaobaounion.moudle

data class GratiaBean(
    val code: Int,
    val `data`: GratiaData,
    val msg: String,
    val success: Boolean
)

data class GratiaData(
    val code: Int,
    val `data`: List<DataX>,
    val message: String,
    val totalCount: Int
)

data class DataX(
    val actStatus: Int,
    val advantage: String,
    val categoryList: List<Category>,
    val content: String,
    val downloadCode: String,
    val downloadUrl: String,
    val eliteId: Int,
    val endTime: Long,
    val id: Int,
    val imgList: List<Img>,
    val platformType: Int,
    val promotionEndTime: Long,
    val promotionStartTime: Long,
    val recommend: Int,
    val startTime: Long,
    val tag: String,
    val title: String,
    val updateTime: Long,
    val urlM: String,
    val urlPC: String
)

data class Category(
    val categoryId: Int,
    val type: Int
)

data class Img(
    val imgName: String,
    val imgUrl: String,
    val widthHeight: String
)