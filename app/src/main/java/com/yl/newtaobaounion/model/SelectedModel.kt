package com.yl.newtaobaounion.model

import com.yl.newtaobaounion.model.dataBean.SelectedBean
import com.yl.newtaobaounion.repository.SelectedRepository

/**
 * @description: 精选界面Model
 * @author YL Chen
 * @date 2024/8/29 14:40
 * @version 1.0
 */
class SelectedModel {
    companion object {
        fun getSelected(
            successCallBack: (selectedBean: SelectedBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ){
            SelectedRepository.getSelectedData(successCallBack,emptyCallBack,errorCallBack)
        }

        fun getMoreSelected(
            successCallBack: (moreSelectedBean: SelectedBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ){
            SelectedRepository.getMoreSelectedData(successCallBack,emptyCallBack,errorCallBack)
        }
    }
}