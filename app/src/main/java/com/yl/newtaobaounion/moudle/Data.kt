package com.yl.newtaobaounion.moudle

data class Data(
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPre: Boolean,
    val list: List<DetailData>,
    val pageSize: Int,
    val total: Int,
    val totalPage: Int
)