package com.yl.newtaobaounion.moudle

data class HotKeyBean(
    val code: Int,
    val `data`: List<HotKeyData>,
    val msg: String,
    val success: Boolean
)

data class HotKeyData(
    val clickCount: Int,
    val color: String,
    val createTime: String,
    val enable: String,
    val id: String,
    val ord: Int,
    val updateTime: String,
    val word: String
)