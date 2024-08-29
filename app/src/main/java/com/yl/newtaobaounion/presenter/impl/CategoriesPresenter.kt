package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.model.CategoriesModel
import com.yl.newtaobaounion.presenter.ICategoriesPresenter
import com.yl.newtaobaounion.view.IonCategoriesDataCallback
//Presenter层只负责视图逻辑，业务逻辑提升到Model层
class CategoriesPresenter private constructor() : ICategoriesPresenter {
    private var onCategoriesDataListener: IonCategoriesDataCallback? = null

    //提供获取此类对象的方法
    companion object {
        private var categoriesPresenter: CategoriesPresenter = CategoriesPresenter()
        fun getInstance(): CategoriesPresenter {
            return categoriesPresenter
        }
    }

    /**
     * 请求分类数据
     */
    /*
        override fun getCategories() {
            //通知ui数据加载中
            onCategoriesDataListener?.onLoading()
            //创建retrofit对象
            val apiInterface = RetrofitCreator.getApiInterface()
            val task = apiInterface?.getCategories()
            task!!.enqueue(object : retrofit2.Callback<CategoriesBean> {
                override fun onResponse(
                    call: Call<CategoriesBean>,
                    response: Response<CategoriesBean>
                ) {
                    if(response.code() == HttpURLConnection.HTTP_OK){
                        LogUtils.d(this@CategoriesPresenter,"HTTP_OK")
                        LogUtils.d(this@CategoriesPresenter,"response-->${response.body()?.data}")
                        val categoriesBean = response.body()
                        if(categoriesBean == null || categoriesBean.data.isEmpty()){
                            //数据为空
                            //回调回View层
                            onCategoriesDataListener?.onEmpty()
                        }else{
                            //数据加载成功
                            //回调回View层
                            onCategoriesDataListener?.onCategoriesDataLoad(categoriesBean)
                        }
                    }else{
                        LogUtils.d(this@CategoriesPresenter,"response.code()-->${response.code()}")
                        //数据加载错误
                        //回调回View层
                        onCategoriesDataListener?.onError()
                    }
                }
                override fun onFailure(call: Call<CategoriesBean>, t: Throwable) {
                    LogUtils.d(this@CategoriesPresenter,"onFailure-->${t.message}")
                    //数据加载错误
                    //回调回View层
                    onCategoriesDataListener?.onError()
                }
            })
        }
    */
    override fun getCategories() {
        //通知View层，数据加载中
        onCategoriesDataListener?.onLoading()
        //通过Model层来请求数据
        CategoriesModel.getCategories(
            //数据请求成功回调
            successCallBack = { categoriesBean ->
                //通知View层，加载数据刷新成功界面
                onCategoriesDataListener?.onCategoriesDataLoad(categoriesBean)
            },
            emptyCallback = {
                //通知View层，加载数据为空界面
                onCategoriesDataListener?.onEmpty()
            },
            errorCallBack = {
                //通知View层，加载数据加载错误界面
                onCategoriesDataListener?.onError()
            }
        )
    }

    override fun registerViewCallback(callback: IonCategoriesDataCallback) {
        this.onCategoriesDataListener = callback
    }

    override fun unregisterViewCallback(callback: IonCategoriesDataCallback) {
        this.onCategoriesDataListener = null
    }

}