package com.yl.newtaobaounion.model.dataBean

data class BannerBean(
    val code: Int,
    val `data`: List<BannerData>,
    val msg: String,
    val success: Boolean
)

data class BannerData(
    val cover: String,
    val createTime: String,
    val enable: String,
    val id: String,
    val ord: Int,
    val size: String,
    val title: String,
    val type: String,
    val updateTime: String,
    val url: String
)