package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.moudle.GratiaBean
import com.yl.newtaobaounion.presenter.IGratiaPresenter
import com.yl.newtaobaounion.view.IGratiaDataCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class GratiaPresenter private constructor():IGratiaPresenter {
    //提供获取此类对象的方法
    companion object{
        private  var gratiaPresenter:GratiaPresenter = GratiaPresenter()
        fun getInstance() :GratiaPresenter{
            return gratiaPresenter
        }
    }

    private  var callback: IGratiaDataCallback? = null

    override fun getGratia() {
        callback?.onLoading()
        val retrofit = RetrofitCreator.getInstance()?.getRetrofit()
        val apiInterface = RetrofitCreator.getInterfaceObject(retrofit)
        val task = apiInterface?.getGratiaData()
        task?.enqueue(object :Callback<GratiaBean>{
            override fun onResponse(call: Call<GratiaBean>, response: Response<GratiaBean>) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    val gratiaBean = response.body()
                    if(gratiaBean?.data != null){
                        //数据加载成功
                        callback?.onGratiaDataLoad(gratiaBean)
                    }else{
                        //数据为空
                        callback?.onEmpty()
                    }
                }else{
                    //数据加载失败
                    callback?.onError()
                }
            }

            override fun onFailure(call: Call<GratiaBean>, t: Throwable) {
                //数据加载失败
                callback?.onError()
            }
        })
    }

    override fun registerViewCallback(callback: IGratiaDataCallback) {
        this.callback = callback
    }

    override fun unregisterViewCallback(callback: IGratiaDataCallback) {
        this.callback = null
    }
}