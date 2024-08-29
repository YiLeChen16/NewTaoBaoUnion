package com.yl.newtaobaounion.model

import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.repository.RecommendRepository

/**
 * @description: 首页推荐数据Model
 * @author YL Chen
 * @date 2024/8/29 10:59
 * @version 1.0
 */
class RecommendModel {
    companion object {
        fun getRecommendByKeyWord(
            keyword: String,
            successCallBack: (recommendBean: RecommendBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            RecommendRepository.getRecommendDataByKeyWord(
                keyword,
                successCallBack,
                emptyCallBack,
                errorCallBack
            )
        }

        fun getMoreRecommendByKeyWord(
            viewKeyword: String,
            keyword: String,
            successCallBack: (loadMoreRecommendBean: RecommendBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            RecommendRepository.getMoreRecommendDataByKeyWord(
                viewKeyword,
                keyword,
                successCallBack,
                emptyCallBack,
                errorCallBack
            )
        }
    }

}