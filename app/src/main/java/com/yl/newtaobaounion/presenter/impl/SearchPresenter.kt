package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.model.SearchModel
import com.yl.newtaobaounion.presenter.ISearchPresenter
import com.yl.newtaobaounion.view.ISearchDataCallback


class SearchPresenter : ISearchPresenter {
    private var callback: ISearchDataCallback? = null


    //提供获取此类对象的方法
    companion object {
        private var selectedPresenter: SearchPresenter = SearchPresenter()

        fun getInstance(): SearchPresenter {
            return selectedPresenter
        }
    }

    override fun getHotkey() {
        //通知View层，搜索关键词数据正在请求中
        callback?.onLoading()
        SearchModel.getHotKey(
            //通知View层，搜索关键词数据请求成功
            successCallback = { hotKeyBean ->
                callback?.onHotKeyLoad(hotKeyBean)
            },
            //通知View层，搜索关键词数据为空
            emptyCallback = {
                callback?.onEmpty()
            },
            //通知View层，搜索关键词数据加载失败
            errorCallback = {
                callback?.onError()
            }
        )
    }

    override fun getSearchData(keyword: String) {
        //通知View层，数据正在加载中
        callback?.onLoading()
        SearchModel.getSearch(
            keyword = keyword,
            //通知View层，数据加载成功
            successCallback = {
                recommendBean ->
                callback?.onSearchDataLoad(recommendBean)
            },
            //通知View层，数据为空
            emptyCallback = {
                callback?.onEmpty()
            },
            //通知View层，数据加载错误
            errorCallback = {
                callback?.onError()
            }
        )
    }

    //加载历史记录
    override fun loadHistoryWord() {
        SearchModel.getHistoriesWord(
            //通知View层，数据加载成功
            successCallback = {
                histories->
                callback?.onHistoryWordLoad(histories.getHistories())
            },
            //通知view层，数据为空
            emptyCallback = {
                callback?.onHistoryEmpty()
            }
        )
    }

    //删除历史记录
    override fun deleteHistory() {
        SearchModel.deleteHistoriesWord()
    }

    //加载更多数据
    override fun onLoadMoreData(keyword: String) {
       SearchModel.getMoreSearch(
           keyword = keyword,
           //通知View层，数据加载成功
           successCallback = {
               recommendBean->
               callback?.onLoadMoreSuccess(recommendBean)
           },
           //通知View层，数据为空
           emptyCallback = {
               callback?.onLoadMoreEmpty()
           },
           //通知View层，数据加载失败
           errorCallback = {
               callback?.onLoadMoreError()
           }
       )
    }

    override fun registerViewCallback(callback: ISearchDataCallback) {
        this.callback = callback
    }

    override fun unregisterViewCallback(callback: ISearchDataCallback) {
        this.callback = null
    }
}