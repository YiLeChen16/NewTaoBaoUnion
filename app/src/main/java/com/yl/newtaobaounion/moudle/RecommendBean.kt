package com.yl.newtaobaounion.moudle

data class RecommendBean(
    val code: Int,
    val `data`: RecommendBeanData,
    val msg: String,
    val success: Boolean
)

data class RecommendBeanData(
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPre: Boolean,
    val list: List<DetailData>,
    val pageSize: Int,
    val total: Int,
    val totalPage: Int
)

data class DetailData(
    val couponAmount: Double,
    val couponRemainCount: Int,
    val couponShareUrl: String,
    val couponTotalCount: Int,
    val cover: String,
    val justPrice: Any,
    val sellCount: Int,
    val source: String,
    val title: String,
    val zkFinalPrice: String
)