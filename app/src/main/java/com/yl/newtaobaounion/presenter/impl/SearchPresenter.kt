package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.model.dataBean.Histories
import com.yl.newtaobaounion.model.dataBean.HotKeyBean
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.presenter.ISearchPresenter
import com.yl.newtaobaounion.utils.JSONCacheUtils
import com.yl.newtaobaounion.utils.LogUtils
import com.yl.newtaobaounion.view.ISearchDataCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection


class SearchPresenter:ISearchPresenter {
    private var callback: ISearchDataCallback? = null
    private val DEFAULT_PAGE: Int = 1
    private val mJsonCacheUtils: JSONCacheUtils
    private var currentKeyword: String? = null
    val MAX_HISTORY_COUNT: Int = 10
    var currentPage = DEFAULT_PAGE//用于记录当前页面


    init {
        //获取数据缓存类的单例
        mJsonCacheUtils = JSONCacheUtils.getInstance();
    }
    //提供获取此类对象的方法
    companion object{
        private  var selectedPresenter:SearchPresenter = SearchPresenter()
        const val KEY_HISTORY: String = "key_history"

        fun getInstance() :SearchPresenter{
            return selectedPresenter
        }
    }

    override fun getHotkey() {
        callback?.onLoading()
        val apiInterface = RetrofitCreator.getApiInterface()
        val task = apiInterface?.getHotKey()
        task?.enqueue(object :Callback<HotKeyBean>{
            override fun onResponse(call: Call<HotKeyBean>, response: Response<HotKeyBean>) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    val hotKeyBean = response.body()
                    if(hotKeyBean?.data != null){
                        callback?.onHotKeyLoad(hotKeyBean)
                    }else{
                        callback?.onEmpty()
                    }
                }else{
                    callback?.onError()
                }
            }

            override fun onFailure(call: Call<HotKeyBean>, t: Throwable) {
                callback?.onError()
            }
        })
    }

    override fun getSearchData(keyword: String) {
        callback?.onLoading()
        //将当前搜索词记录下来,以便重新搜索
        if (keyword != currentKeyword) {
            this.currentKeyword = keyword
            //保存搜索历史记录
            saveHistory(keyword)
        }
        val apiInterface = RetrofitCreator.getApiInterface()
        val task =
            apiInterface?.getRecommendedByKeyword(DEFAULT_PAGE.toString(),keyword)
        task?.enqueue(object :Callback<RecommendBean>{
            override fun onResponse(call: Call<RecommendBean>, response: Response<RecommendBean>) {
                LogUtils.d(this@SearchPresenter,"response.code()-->${response.code()}")
                if(response.code() == HttpURLConnection.HTTP_OK){
                    val recommendBean = response.body()
                    LogUtils.d(this@SearchPresenter,"recommendBean?.data?.list-->${recommendBean?.data?.list}")
                    if(recommendBean?.data?.list != null){
                        callback?.onSearchDataLoad(recommendBean)
                    }else{
                        callback?.onEmpty()
                    }
                }else{
                    callback?.onError()
                }
            }

            override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                LogUtils.d(this@SearchPresenter,"onFailure-->${t.message}")
                callback?.onError()
            }
        })
    }

    //保存搜索历史记录
    private fun saveHistory(keyword:String){
        //TODO：：
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

    //加载历史记录
    override fun loadHistoryWord() {
        val histories: Histories? = mJsonCacheUtils.getCache(
            KEY_HISTORY,
            Histories::class.java
        )
        if (histories?.getHistories() != null && histories.getHistories().size !== 0
        ) {
            LogUtils.d(this@SearchPresenter,"loadHistoryWord-->${histories.getHistories()}")
           callback?.onHistoryWordLoad(histories.getHistories())
        } else {
            //没有历史记录，可能是由于用户点击了清空历史记录，故需要通知UI将FlowTextLayout中现有的子View清空
            callback?.onHistoryEmpty()
        }
    }

    //删除历史记录
    override fun deleteHistory() {
        mJsonCacheUtils.deleteCache(KEY_HISTORY)
    }

    //加载更多数据
    override fun onLoadMoreData(keyword: String) {
        val apiInterface = RetrofitCreator.getApiInterface()
        currentPage++
        val task =
            apiInterface?.getRecommendedByKeyword(currentPage.toString(), keyword)
        task?.enqueue(object :Callback<RecommendBean>{
            override fun onResponse(call: Call<RecommendBean>, response: Response<RecommendBean>) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    val recommendBean = response.body()
                    LogUtils.d(this@SearchPresenter,"recommendBean-->$recommendBean")
                    if(recommendBean?.data?.list != null){
                        callback?.onLoadMoreSuccess(recommendBean)
                    }else{
                        currentPage--
                        callback?.onLoadMoreEmpty()
                    }
                }else{
                    currentPage--
                    callback?.onLoadMoreError()
                }
            }

            override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                LogUtils.d(this@SearchPresenter,"onFailure-->$t")
                currentPage--
                callback?.onLoadMoreError()
            }
        })
    }

    override fun registerViewCallback(callback: ISearchDataCallback) {
        this.callback = callback
    }

    override fun unregisterViewCallback(callback: ISearchDataCallback) {
        this.callback = null
    }
}