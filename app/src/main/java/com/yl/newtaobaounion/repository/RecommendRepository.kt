package com.yl.newtaobaounion.repository

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @description: 首页推荐数据仓库，负责请求推荐数据并存储数据
 * @author YL Chen
 * @date 2024/8/29 11:00
 * @version 1.0
 */
class RecommendRepository {


    companion object{
        private val apiInterface = RetrofitCreator.getApiInterface()
        //默认加载的页数
        private val DEFAULT_PAGE = 1
        //请求的第一页推荐数据
        var recommendBean: RecommendBean? = null
        //创建一个键值对集合用于存储对应view页面的关键字和其当前加载的页面页数
        private var viewListInfo =
            mutableMapOf<Pair<String, String>, Int>()//键代表的是对应页面关键字及其细分标签<viewKeyword,keyword>,值代表的是该页面目前的页面数
        //请求的更多推荐数据
        var loadMoreRecommendBean: RecommendBean? = null

        //通过关键词请求第一页推荐数据
        fun getRecommendDataByKeyWord(
            keyword: String,
            successCallBack: (recommendBean: RecommendBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            //默认加载第一页的数据
            val task =
                apiInterface?.getRecommendedByKeyword(DEFAULT_PAGE.toString(), keyword)
            task?.enqueue(object : Callback<RecommendBean> {
                override fun onResponse(call: Call<RecommendBean>, response: Response<RecommendBean>) {
                    if (response.isSuccessful) {
                        recommendBean = response.body()
                        LogUtils.d(this@Companion, "recommendBean-->$recommendBean")
                        recommendBean.let {
                            if (it?.data?.list == null) {
                                //数据为空
                                emptyCallBack()
                            } else {
                                //数据请求成功
                                successCallBack(recommendBean!!)
                            }
                        }
                    } else {
                        //数据请求错误
                        errorCallBack()
                    }
                }

                override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                    LogUtils.d(this@Companion, "onFailure-->${t.message}")
                    //数据请求错误
                    errorCallBack()
                }
            })
        }

        //根据关键词，加载更多数据
        fun getMoreRecommendDataByKeyWord(
            viewKeyword: String,
            keyword: String,
            successCallBack: (recommendBean: RecommendBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            //获取到当前页面及细分标签加载到的页面数
            var currentPage = viewListInfo[Pair(viewKeyword, keyword)]
            //若其中没有数据，则为第一页
            if (currentPage == null) {
                currentPage = 1
            }
            //当前页面加一
            currentPage++
            //更新到集合中
            viewListInfo[Pair(viewKeyword, keyword)] = currentPage
            LogUtils.d(this, "currentPage-->$currentPage")
            //请求数据
            val task =
                apiInterface?.getRecommendedByKeyword(currentPage.toString(), keyword)
            task?.enqueue(
                object : Callback<RecommendBean> {
                    override fun onResponse(
                        call: Call<RecommendBean>,
                        response: Response<RecommendBean>
                    ) {
                        if (response.isSuccessful) {
                            loadMoreRecommendBean = response.body()
                            LogUtils.d(
                                this@Companion,
                                "loadMoreRecommendBean-->$loadMoreRecommendBean"
                            )
                            loadMoreRecommendBean.let {
                                if (it?.data?.list != null) {
                                    //数据加载成功
                                    successCallBack(loadMoreRecommendBean!!)

                                } else {
                                    //将currentPage还原
                                    currentPage--
                                    viewListInfo[Pair(viewKeyword, keyword)] = currentPage
                                    //数据为空
                                    emptyCallBack()
                                }
                            }
                        } else {
                            //将currentPage还原
                            currentPage--
                            viewListInfo[Pair(viewKeyword, keyword)] = currentPage
                            //数据加载错误
                            errorCallBack()
                        }
                    }

                    override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                        LogUtils.d(this@Companion, "onFailure-->${t.message}")
                        //将currentPage还原
                        currentPage--
                        viewListInfo[Pair(viewKeyword, keyword)] = currentPage
                        //数据加载错误
                        errorCallBack()
                    }
                }
            )
        }
    }




}