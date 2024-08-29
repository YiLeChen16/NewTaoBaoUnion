package com.yl.newtaobaounion.model

import com.yl.newtaobaounion.model.dataBean.GratiaBean
import com.yl.newtaobaounion.repository.GratiaRepository

/**
 * @description:特惠界面Model
 * @author YL Chen
 * @date 2024/8/29 15:19
 * @version 1.0
 */
class GratiaModel {
    companion object {
        fun getGratia(
            successCallback: (gratiaBean: GratiaBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallback: () -> Unit,
        ) {
            GratiaRepository.getGratiaData(successCallback,emptyCallback,errorCallback)
        }
    }
}