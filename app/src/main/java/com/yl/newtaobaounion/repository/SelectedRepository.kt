package com.yl.newtaobaounion.repository

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.model.dataBean.SelectedBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @description: 精选数据仓库，负责请求精选界面数据及存储
 * @author YL Chen
 * @date 2024/8/29 14:41
 * @version 1.0
 */
class SelectedRepository {
    companion object {
        val apiInterface = RetrofitCreator.getApiInterface()
        //默认加载第一页
        private val DEFAULT_PAGE = 1
        //请求的第一页精选数据
        var selectedBean: SelectedBean? = null
        //加载更多的精选数据
        var loadMoreSelectedBean: SelectedBean? = null
        //用于存储当前加载的页面数
        private var currentPage = DEFAULT_PAGE

        //加载第一页精选数据
        fun getSelectedData(
            successCallBack: (selectedBean: SelectedBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            val task = apiInterface?.getSelectedGoods(DEFAULT_PAGE.toString())
            task?.enqueue(
                object : Callback<SelectedBean> {
                    override fun onResponse(
                        call: Call<SelectedBean>,
                        response: Response<SelectedBean>
                    ) {
                        if (response.isSuccessful) {
                            selectedBean = response.body()
                            if (selectedBean?.data?.list != null) {
                                //数据请求成功且不为空
                                successCallBack(selectedBean!!)
                            } else {
                                //数据为空
                                emptyCallBack()
                            }
                        } else {
                            //数据请求失败
                            errorCallBack()
                        }
                    }

                    override fun onFailure(call: Call<SelectedBean>, t: Throwable) {
                        //数据请求失败
                        errorCallBack()
                    }
                }
            )
        }

        //加载更多精选数据
        fun getMoreSelectedData(
            successCallBack: (selectedBean: SelectedBean) -> Unit,
            emptyCallBack: () -> Unit,
            errorCallBack: () -> Unit
        ) {
            //当前页码数+1
            currentPage++
            val task = apiInterface?.getSelectedGoods(currentPage.toString())
            task?.enqueue(
                object : Callback<SelectedBean> {
                    override fun onResponse(
                        call: Call<SelectedBean>,
                        response: Response<SelectedBean>
                    ) {
                        if (response.isSuccessful) {
                            loadMoreSelectedBean = response.body()
                            if (loadMoreSelectedBean?.data?.list != null) {
                                //数据请求成功
                                successCallBack(loadMoreSelectedBean!!)
                            } else {
                                //恢复当前页码数
                                currentPage--
                                //数据为空
                                emptyCallBack()
                            }
                        } else {
                            //恢复当前页码数
                            currentPage--
                            //数据请求失败
                            errorCallBack()
                        }
                    }

                    override fun onFailure(call: Call<SelectedBean>, t: Throwable) {
                        //恢复当前页码数
                        currentPage--
                        //数据请求失败
                        errorCallBack()
                    }
                }
            )
        }
    }
}