package com.yl.newtaobaounion.repository

import com.tencent.mmkv.MMKV
import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.model.dataBean.HotKeyBean
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors


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
        //获取MMVK全局实例
        var kv: MMKV = MMKV.defaultMMKV()
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
            successCallback: (set: Set<String>) -> Unit,
            emptyCallback: () -> Unit
        ) {
            val stringSet = kv.decodeStringSet(KEY_HISTORY)
            if(stringSet == null){
                //数据为空
                emptyCallback()
            }else{
                //数据加载成功
                successCallback(stringSet)
            }
            /*if (histories?.getHistories() != null && histories.getHistories().size !== 0
            ) {
                LogUtils.d(this@Companion, "loadHistoryWord-->${histories.getHistories()}")
                //数据加载成功
                successCallback(histories)
            } else {
                //数据为空
                emptyCallback()
            }*/
        }

        //删除历史关键词数据
        fun deleteHistoriesData() {
            //mJsonCacheUtils.deleteCache(KEY_HISTORY)
            kv.remove(KEY_HISTORY)
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


        //保存历史数据（MMVK）
        private fun saveHistory(keyword: String) {
            val data = kv.decodeStringSet(KEY_HISTORY)
            //历史数据集合
            var set:MutableSet<String>?
            if(data == null){
                //无历史数据
                //创建并添加历史数据集合
                set = mutableSetOf()
                set.add(keyword)
                kv.encode(KEY_HISTORY,set)
            }else{
                //有历史数据
                //判断当前集合中是否包含此关键词
                if(data.contains(keyword)){
                    //将之前的关键词去除
                    data.remove(keyword)
                }
                //判断历史数据个数是否大于最大值
                if(data.size > MAX_HISTORY_COUNT){
                    //将当前关键词添加进去
                    data.add(keyword)
                    //将set转化为list
                    val list = data.stream().collect(Collectors.toList())
                    //裁剪集合
                    val subList = list.subList(list.size - 10,list.size)
                    //转回set
                    val subSet = subList.stream().collect(Collectors.toSet())
                    //存回MMKV
                    kv.encode(KEY_HISTORY,subSet)
                }else{
                    //直接添加
                    data.add(keyword)
                    LogUtils.d(this@Companion,"data-->${data}")
                    kv.encode(KEY_HISTORY,data)
                }
            }
        }
    }
}