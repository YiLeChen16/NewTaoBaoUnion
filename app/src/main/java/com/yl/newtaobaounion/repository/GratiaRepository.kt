package com.yl.newtaobaounion.repository

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.model.dataBean.GratiaBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @description: TODO
 * @author YL Chen
 * @date 2024/8/29 15:20
 * @version 1.0
 */
class GratiaRepository {
    companion object {
        private val apiInterface = RetrofitCreator.getApiInterface()

        //请求的特惠数据
        var gratiaBean: GratiaBean? = null

        //加载全部特惠数据
        fun getGratiaData(
            successCallback: (gratiaBean: GratiaBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallback: () -> Unit,
        ) {
            val task = apiInterface?.getGratiaData()
            task?.enqueue(
                object : Callback<GratiaBean> {
                    override fun onResponse(
                        call: Call<GratiaBean>,
                        response: Response<GratiaBean>
                    ) {
                        if (response.isSuccessful) {
                            gratiaBean = response.body()
                            if (gratiaBean?.data != null) {
                                //数据请求成功
                                successCallback(gratiaBean!!)
                            } else {
                                //数据为空
                                emptyCallback()
                            }
                        } else {
                            //数据请求失败
                            errorCallback()
                        }
                    }

                    override fun onFailure(call: Call<GratiaBean>, t: Throwable) {
                        //数据请求失败
                        errorCallback()
                    }
                }
            )
        }
    }

}