package com.yl.newtaobaounion.repository

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.model.dataBean.CategoriesBean
import com.yl.newtaobaounion.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * @description: 分类数据仓库，负责请求分类数据，并存储数据
 * @author YL Chen
 * @date 2024/8/28 17:06
 * @version 1.0
 */
class CategoriesRepository {

    companion object {
        private val apiInterface = RetrofitCreator.getApiInterface()
        var categoriesBean: CategoriesBean? = null

        //进行分类数据的网络请求并将数据存储在此类
        fun getCategoriesData(
            successCallBack: (categoriesBean: CategoriesBean) -> Unit,
            emptyCallback: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            val task = apiInterface?.getCategories()
            task?.enqueue(object : Callback<CategoriesBean> {
                override fun onResponse(
                    call: Call<CategoriesBean>,
                    response: Response<CategoriesBean>
                ) {
                    if (response.isSuccessful) {
                        LogUtils.d(this@Companion, "HTTP_OK")
                        LogUtils.d(this@Companion, "response-->${response.body()?.data}")
                        categoriesBean = response.body()
                        categoriesBean.let { currentCategoriesBean ->
                            if (currentCategoriesBean == null || currentCategoriesBean.data.isEmpty()) {
                                //数据为空
                                emptyCallback()
                            } else {
                                //数据加载成功
                                successCallBack(categoriesBean!!)
                            }
                        }
                    } else {
                        LogUtils.d(
                            this@Companion,
                            "response.code()-->${response.code()}"
                        )
                        //数据加载错误
                        errorCallBack()
                    }
                }

                override fun onFailure(call: Call<CategoriesBean>, t: Throwable) {
                    LogUtils.d(this@Companion, "onFailure-->${t.message}")
                    errorCallBack()
                }
            })
        }

    }
}