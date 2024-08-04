package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.moudle.SelectedBean
import com.yl.newtaobaounion.presenter.ISelectedPresenter
import com.yl.newtaobaounion.utils.LogUtils
import com.yl.newtaobaounion.view.ISelectedDataCallback
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection

class SelectedPresenter private constructor():ISelectedPresenter {
    //提供获取此类对象的方法
    companion object{
        private  var selectedPresenter:SelectedPresenter = SelectedPresenter()
        fun getInstance() :SelectedPresenter{
            return selectedPresenter
        }
    }

    private var callback: ISelectedDataCallback? = null
    private val DEFAULT_PAGE = 1
    //用于存储当前加载的页面数
    private var currentPage = DEFAULT_PAGE

    override fun getSelectedData() {
        callback?.onLoading()
        val retrofit = RetrofitCreator.getInstance()?.getRetrofit()
        val interfaceObject = RetrofitCreator.getInterfaceObject(retrofit)
        val task = interfaceObject?.getSelectedGoods(DEFAULT_PAGE.toString())/* 默认加载第一页 */
        task?.enqueue(object :retrofit2.Callback<SelectedBean>{
            override fun onResponse(call: Call<SelectedBean>, response: Response<SelectedBean>) {
                LogUtils.d(this@SelectedPresenter,"response-->${response.code()}")
                if(response.code() == HttpURLConnection.HTTP_OK){
                    val selectedBean = response.body()
                    LogUtils.d(this@SelectedPresenter,"selectedBean-->${selectedBean}")
                    if(selectedBean?.data?.list != null){
                        //数据请求成功
                        callback?.onSelectedDataLoad(selectedBean)
                    }else{
                        //数据为空
                        callback?.onEmpty()
                    }
                }else{
                    //加载错误
                    callback?.onError()
                }
            }
            override fun onFailure(call: Call<SelectedBean>, t: Throwable) {
                //加载错误
                callback?.onError()
            }
        })
    }

    override fun getMoreSelectedData() {
        //当前页码数+1
        currentPage++
        val retrofit = RetrofitCreator.getInstance()?.getRetrofit()
        val interfaceObject = RetrofitCreator.getInterfaceObject(retrofit)
        val task = interfaceObject?.getSelectedGoods(currentPage.toString())
        task?.enqueue(object :retrofit2.Callback<SelectedBean>{
            override fun onResponse(call: Call<SelectedBean>, response: Response<SelectedBean>) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    val selectedBean = response.body()
                    if(selectedBean?.data?.list != null){
                        //数据请求成功
                        callback?.onMoreSelectedDataLoad(selectedBean)
                    }else{
                        //数据为空
                        callback?.onMoreSelectedDataEmpty()
                        currentPage--//恢复当前页码数
                    }
                }else{
                    //加载错误
                    callback?.onMoreSelectedDataError()
                    currentPage--//恢复当前页码数
                }
            }
            override fun onFailure(call: Call<SelectedBean>, t: Throwable) {
                //加载错误
                callback?.onMoreSelectedDataError()
                currentPage--//恢复当前页码数
            }
        })
    }

    override fun registerViewCallback(callback: ISelectedDataCallback) {
        this.callback = callback
    }

    override fun unregisterViewCallback(callback: ISelectedDataCallback) {
        this.callback = null
    }
}