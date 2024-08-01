package com.yl.newtaobaounion.https

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitCreator//在构造方法里创建并配置Retrofit
private constructor() {
    //声明baseURL和请求超时时间
    val BASE_URL = "https://api.taobaounion.cn"
    val CONNECT_TIME_OUT = 10000 //毫秒

    companion object {
        private var sRetrofitCreator: RetrofitCreator? = null

        //对外提供获取RetrofitCreator单例的方法，使用synchronized保证单一实例，避免多个地方同时调用时重复创建单例
        fun getInstance(): RetrofitCreator? {
            if (sRetrofitCreator == null) {
                synchronized(RetrofitCreator::class.java) {
                    if (sRetrofitCreator == null) {
                        sRetrofitCreator = RetrofitCreator()
                    }
                }
            }
            return sRetrofitCreator
        }

        //获取接口对象
        fun getInterfaceObject(retrofit: Retrofit?): ApiInterface? {
            return retrofit?.create(ApiInterface::class.java)
        }
    }

    //声明Retrofit对象
    private var mRetrofit: Retrofit? = null

    init {
        createRetrofit()
    }

    //创建并配置Retrofit
    private fun createRetrofit() {
        //结合okhttp进行网络请求
        //设置一下okHttp的参数
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
            .build()
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) //设置BaseUrl
            .client(okHttpClient) //设置请求的client
            .addConverterFactory(GsonConverterFactory.create()) //设置转换器
            .build()
    }


    //获取Retrofit对象
    fun getRetrofit(): Retrofit? {
        return mRetrofit
    }


}