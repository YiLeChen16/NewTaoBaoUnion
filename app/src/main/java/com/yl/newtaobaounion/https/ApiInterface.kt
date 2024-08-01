package com.yl.newtaobaounion.https

import com.yl.newtaobaounion.moudle.CategoriesBean
import com.yl.newtaobaounion.moudle.RecommendBean
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//网络请求接口
interface ApiInterface {

    /**
     * 请求首页分类数据
     */
    @GET("/shop/category/list")
    fun getCategories(): Call<CategoriesBean>


    /**
     * 根据关键字请求推荐商品数据
     */
    @GET("/shop/s/{page}")
    fun getRecommendedByKeyword(
        @Path("page") page:String,
        @Query("k") k: String
    ): Call<RecommendBean>

}