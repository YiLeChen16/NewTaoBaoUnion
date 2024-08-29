package com.yl.newtaobaounion.repository

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.model.dataBean.Histories
import com.yl.newtaobaounion.model.dataBean.HotKeyBean
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.utils.JSONCacheUtils
import com.yl.newtaobaounion.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @description: 搜索界面数据仓库，负责请求界面数据并存储界面数据
 * @author YL Chen
 * @date 2024/8/29 15:41
 * @version 1.0
 */
class SearchRepository {
    companion object {
        val apiInterface = RetrofitCreator.getApiInterface()
        //默认加载第一页数据
        private val DEFAULT_PAGE: Int = 1
        //请求的推荐热词数据
        var hotKeyBean: HotKeyBean? = null
        //当前搜索的关键词
        private var currentKeyword: String? = null
        //请求的搜索数据
        var recommendBean: RecommendBean? = null
        //获取数据缓存类的单例
        private val mJsonCacheUtils: JSONCacheUtils = JSONCacheUtils.getInstance()
        //最大历史关键词个数
        val MAX_HISTORY_COUNT: Int = 10
        //当前搜索页面数
        var currentPage = DEFAULT_PAGE
        //请求的加载更多的搜索数据
        var loadMoreRecommendBean: RecommendBean? = null

        const val KEY_HISTORY: String = "key_history"

        //加载推荐热词
        fun getHotKeyData(
            successCallback: (hotKeyBean: HotKeyBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallback: () -> Unit,
        ) {
            val task = apiInterface?.getHotKey()
            task?.enqueue(object : Callback<HotKeyBean> {
                override fun onResponse(
                    call: Call<HotKeyBean>, response: Response<HotKeyBean>
                ) {
                    if (response.isSuccessful) {
                        hotKeyBean = response.body()
                        if (hotKeyBean?.data != null) {
                            //数据请求成功
                            successCallback(hotKeyBean!!)
                        } else {
                            //数据为空
                            emptyCallback()
                        }
                    } else {
                        //数据请求失败
                        errorCallback()
                    }
                }

                override fun onFailure(call: Call<HotKeyBean>, t: Throwable) {
                    //数据请求失败
                    errorCallback()
                }
            })
        }

        //加载搜索数据
        fun getSearchData(
            keyword: String,
            successCallback: (recommendBean: RecommendBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallback: () -> Unit,
        ) {
            //将搜索的关键词保存下来
            if (keyword != currentKeyword) {
                this.currentKeyword = keyword
                saveHistory(keyword)
            }
            val task =
                apiInterface?.getRecommendedByKeyword(DEFAULT_PAGE.toString(), keyword)
            task?.enqueue(
                object : Callback<RecommendBean> {
                    override fun onResponse(
                        call: Call<RecommendBean>,
                        response: Response<RecommendBean>
                    ) {
                        if (response.isSuccessful) {
                            recommendBean = response.body()
                            if (recommendBean?.data?.list != null) {
                                //数据请求成功
                                successCallback(recommendBean!!)
                            } else {
                                //数据为空
                                emptyCallback()
                            }
                        } else {
                            //数据请求失败
                            errorCallback()
                        }
                    }

                    override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                        //数据请求失败
                        errorCallback()
                    }
                }
            )
        }

        //加载历史关键词数据
        fun getHistoryWordData(
            successCallback: (histories: Histories) -> Unit,
            emptyCallback: () -> Unit
        ) {
            val histories: Histories? = mJsonCacheUtils.getCache(
                KEY_HISTORY,
                Histories::class.java
            )
            if (histories?.getHistories() != null && histories.getHistories().size !== 0
            ) {
                LogUtils.d(this@Companion, "loadHistoryWord-->${histories.getHistories()}")
                //数据加载成功
                successCallback(histories)
            } else {
                //数据为空
                emptyCallback()
            }
        }

        //删除历史关键词数据
        fun deleteHistoriesData() {
            mJsonCacheUtils.deleteCache(KEY_HISTORY)
        }

        //加载更多搜索数据
        fun getMoreSearchData(
            keyword: String,
            successCallback: (recommendBean: RecommendBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallback: () -> Unit,
        ) {
            currentPage++
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
                            if (loadMoreRecommendBean?.data?.list != null) {
                                //数据加载成功
                                successCallback(loadMoreRecommendBean!!)
                            } else {
                                //将currentPage还原
                                currentPage--
                                //数据为空
                                emptyCallback()
                            }
                        } else {
                            //将currentPage还原
                            currentPage--
                            //数据加载错误
                            errorCallback()
                        }
                    }

                    override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                        //将currentPage还原
                        currentPage--
                        //数据加载错误
                        errorCallback()
                    }
                }
            )
        }

        //保存历史数据
        private fun saveHistory(keyword: String) {
            var histories = mJsonCacheUtils.getCache(KEY_HISTORY, Histories::class.java)

            //之前有历史记录，去重
            var historyList: MutableList<String>? = null
            if (histories?.getHistories() != null) {
                historyList = histories.getHistories().toMutableList()
                if (historyList.contains(keyword)) {
                    historyList.remove(keyword)
                }
            }
            //已完成去重
            if (historyList == null) {
                historyList = ArrayList()
            }
            if (histories == null) {
                histories = Histories()
            }

            //将新的历史数据添加到历史记录集合中
            historyList.add(keyword)

            //控制历史记录数量在十个以内
            if (historyList.size > MAX_HISTORY_COUNT) {
                //对集合进行裁剪
                historyList = historyList.subList(historyList.size - 10, historyList.size)
            }
            histories.setHistories(historyList.toList())

            //将历史记录集合进行缓存
            mJsonCacheUtils.saveCache(
                KEY_HISTORY,
                histories
            )
        }

    }
}