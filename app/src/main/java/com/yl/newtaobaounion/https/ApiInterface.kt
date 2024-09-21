package com.yl.newtaobaounion.https

import com.yl.newtaobaounion.model.dataBean.BannerBean
import com.yl.newtaobaounion.model.dataBean.CategoriesBean
import com.yl.newtaobaounion.model.dataBean.GratiaBean
import com.yl.newtaobaounion.model.dataBean.HotKeyBean
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.model.dataBean.SelectedBean
import retrofit2.Call
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
     * @param page String 页面
     * @param k String 关键词
     * @return Call<RecommendBean>
     */
    @GET("/shop/s/{page}")
    fun getRecommendedByKeyword(
        @Path("page") page:String,
        @Query("k") k: String
    ): Call<RecommendBean>

    /**
     * 获取精选界面的数据
     * @param page String 页面
     */
    @GET("/shop/r/{page}")
    fun getSelectedGoods(
        @Path("page") page:String
    ):Call<SelectedBean>


    /**
     * 获取特惠活动数据
     * @return Call<GratiaBean>
     */
    @GET("/shop/act")
    fun getGratiaData():Call<GratiaBean>

    /**
     * 获取搜索推荐热词
     * @return Call<HotKeyBean>
     */
    @GET("/shop/search-word")
    fun getHotKey():Call<HotKeyBean>

    /**
     * 获取轮播图数据
     * @param count String 请求轮播图数量
     * @return Call<BannerBean>
     */
    @GET("/shop/banner")
    fun getBanner(
        @Query("count") count:String
    ):Call<BannerBean>
}