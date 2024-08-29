package com.yl.newtaobaounion.model

import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.model.dataBean.Histories
import com.yl.newtaobaounion.model.dataBean.HotKeyBean
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.repository.SearchRepository

/**
 * @description: 搜索界面Model
 * @author YL Chen
 * @date 2024/8/29 15:40
 * @version 1.0
 */
class SearchModel {
    companion object{
        //加载推荐热词
        fun getHotKey(
            successCallback: (hotKeyBean: HotKeyBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallback: () -> Unit,
        ){
            SearchRepository.getHotKeyData(successCallback,emptyCallback,errorCallback)
        }

        //加载第一页搜索推荐数据
        fun getSearch(
            keyword:String,
            successCallback: (recommendBean: RecommendBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallback: () -> Unit
        ){
            SearchRepository.getSearchData(keyword,successCallback,emptyCallback,errorCallback)
        }

        //加载更多推荐数据
        fun getMoreSearch(
            keyword:String,
            successCallback: (recommendBean: RecommendBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallback: () -> Unit
        ){
            SearchRepository.getMoreSearchData(keyword,successCallback,emptyCallback,errorCallback)
        }

        //加载历史搜索关键词数据
        fun getHistoriesWord(
            successCallback: (histories: Histories) -> Unit,
            emptyCallback: () -> Unit
        ){
            SearchRepository.getHistoryWordData(successCallback,emptyCallback)
        }

        //删除历史搜索关键词数据
        fun deleteHistoriesWord(){
            SearchRepository.deleteHistoriesData()
        }
    }
}