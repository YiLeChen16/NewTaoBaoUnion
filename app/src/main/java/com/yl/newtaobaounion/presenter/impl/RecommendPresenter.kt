package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.https.RetrofitCreator
import com.yl.newtaobaounion.moudle.RecommendBean
import com.yl.newtaobaounion.presenter.IRecommendPresenter
import com.yl.newtaobaounion.utils.LogUtils
import com.yl.newtaobaounion.view.IRecommendDataCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
//私有化构造方法
//由于这个presenter会被首页的多个fragment使用，故直接私有化构造方法，并对外提供单例获取此类的对象，避免重复创建对象
class RecommendPresenter private constructor():IRecommendPresenter {

    //提供获取此类对象的方法
    companion object{
        private  var recommendPresenter:RecommendPresenter = RecommendPresenter()
        fun getInstance() :RecommendPresenter{
            return recommendPresenter
        }
    }

    //创建一个集合用于存储注册过来的view层通知接口
    private var viewCallbackList:MutableSet<IRecommendDataCallback> = mutableSetOf()
    //创建一个键值对集合用于存储对应view页面的关键字和其当前加载的页面页数
    private var viewListInfo = mutableMapOf<Pair<String,String>,Int>()//键代表的是对应页面关键字及其细分标签<viewKeyword,keyword>,值代表的是该页面目前的页面数
    //默认加载的页数
    private val DEFAULT_PAGE = 1

    //加载数据
    override fun getRecommendDataByKeyWord(viewKeyword: String,keyword: String,refresh:Boolean) {
        //根据当前页面的关键字确定需要通知到哪个页面
        if(!refresh){
            onDataLoadingByViewKeyword(viewKeyword)
        }
        //callback?.onLoading()
        val retrofit = RetrofitCreator.getInstance()?.getRetrofit()
        val apiInterface = RetrofitCreator.getInterfaceObject(retrofit)
        //默认加载第一页的数据
        val task = apiInterface?.getRecommendedByKeyword(DEFAULT_PAGE.toString(),keyword)
        task?.enqueue(object : Callback<RecommendBean>{
            override fun onResponse(call: Call<RecommendBean>, response: Response<RecommendBean>) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    val recommendBean = response.body()
                    LogUtils.d(this@RecommendPresenter,"recommendBean-->$recommendBean")
                    if(recommendBean?.data?.list != null){
                        if(refresh){
                            onReDataSuccessByViewKeyword(viewKeyword,recommendBean)
                        }else{
                            onDataSuccessByViewKeyword(viewKeyword,recommendBean)
                        }
                    }else{
                        if(refresh){
                            onReDataEmptyByViewKeyword(viewKeyword)
                        }else{
                            onDataEmptyByViewKeyword(viewKeyword)
                        }
                    }
                }else{
                    if(refresh){
                        onReDataErrorByViewKeyword(viewKeyword)
                    }else{
                        onDataErrorByViewKeyword(viewKeyword)
                    }
                }
            }
            override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                LogUtils.d(this@RecommendPresenter,"onFailure-->${t.message}")
                if(refresh){
                    onReDataErrorByViewKeyword(viewKeyword)
                }else{
                    onDataErrorByViewKeyword(viewKeyword)
                }
            }
        })
    }

    //加载更多数据
    override fun loadMoreRecommendDataByKeyWord(viewKeyword: String,keyword: String) {
        //获取到当前页面及细分标签加载到的页面数
        var currentPage = viewListInfo[Pair(viewKeyword, keyword)]
        //若其中没有数据，则为第一页
        if(currentPage == null){
            currentPage = 1
        }
        //当前页面加一
        currentPage++
        //更新到集合中
        viewListInfo[Pair(viewKeyword, keyword)] = currentPage
        LogUtils.d(this,"currentPage-->$currentPage")
        //请求数据
        val retrofit = RetrofitCreator.getInstance()?.getRetrofit()
        val apiInterface = RetrofitCreator.getInterfaceObject(retrofit)
        val task =
            apiInterface?.getRecommendedByKeyword(currentPage.toString(), keyword)
        task?.enqueue(object :Callback<RecommendBean>{
            override fun onResponse(call: Call<RecommendBean>, response: Response<RecommendBean>) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    val recommendBean = response.body()
                    LogUtils.d(this@RecommendPresenter,"recommendBean-->$recommendBean")
                    if(recommendBean?.data?.list != null){
                        onMoreDataSuccessByViewKeyword(viewKeyword,recommendBean)
                    }else{
                        //将currentPage还原
                        currentPage--
                        viewListInfo[Pair(viewKeyword, keyword)] = currentPage
                        if(recommendBean?.code == 41111){
                            //加载失败
                            onMoreDataErrorByViewKeyword(viewKeyword)
                        }else{
                            onMoreDataEmptyByViewKeyword(viewKeyword)
                        }
                    }
                }else{
                    //将currentPage还原
                    currentPage--
                    viewListInfo[Pair(viewKeyword, keyword)] = currentPage
                    onMoreDataErrorByViewKeyword(viewKeyword)
                }
            }

            override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                onMoreDataErrorByViewKeyword(viewKeyword)
            }

        })
    }

    //刷新数据，直接回调之前请求数据的方法即可
    override fun reLoadRecommendDataByKeyWord(viewKeyword: String,keyword: String) {
        //refresh标志为true，表示为刷新的数据
        getRecommendDataByKeyWord(viewKeyword,keyword,true)
    }

    /**
     * 回调view层接口函数
     */

    //刷新成功
    private fun onReDataSuccessByViewKeyword(viewKeyword: String,recommendBean: RecommendBean){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onRefreshDataLoadSuccess(recommendBean)
            }
        }
    }

    //刷新失败
    private fun onReDataErrorByViewKeyword(viewKeyword: String){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onRefreshDataLoadError()
            }
        }
    }

    //刷新为空
    private fun onReDataEmptyByViewKeyword(viewKeyword: String){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onRefreshDataLoadEmpty()
            }
        }
    }

    //根据当前页面的关键字确定需要通知到哪个页面，通知对应View层数据正在加载中
    private fun onDataLoadingByViewKeyword(viewKeyword: String){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onLoading()
            }
        }
    }

    //根据当前页面的关键字确定需要通知到哪个页面，通知对应View层数据加载错误
    private fun onDataErrorByViewKeyword(viewKeyword: String){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onError()
            }
        }
    }

    //根据当前页面的关键字确定需要通知到哪个页面，通知对应View层数据加载成功
    private fun onDataSuccessByViewKeyword(viewKeyword: String,recommendBean: RecommendBean){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onRecommendDataLoad(recommendBean)
            }
        }
    }

    //根据当前页面的关键字确定需要通知到哪个页面，通知对应View层数据为空
    private fun onDataEmptyByViewKeyword(viewKeyword: String){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onEmpty()
            }
        }
    }

    //加载更多数据，通知对应View层数据加载成功
    private fun onMoreDataSuccessByViewKeyword(viewKeyword: String,recommendBean: RecommendBean){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onMoreDataLoadSuccess(recommendBean)
            }
        }
    }

    //加载更多数据，通知对应View层数据加载失败
    private fun onMoreDataErrorByViewKeyword(viewKeyword: String){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onMoreDataLoadError()
            }
        }
    }

    //加载更多数据，通知对应View层数据加载为空
    private fun onMoreDataEmptyByViewKeyword(viewKeyword: String){
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if(iRecommendDataCallback.getViewKeyWord() == viewKeyword){
                iRecommendDataCallback.onMoreDataLoadEmpty()
            }
        }
    }

    override fun registerViewCallback(callback: IRecommendDataCallback) {
        //将注册过来的view层回调接口保存到集合中
        viewCallbackList.add(callback)
        //this.callback = callback
    }

    override fun unregisterViewCallback(callback: IRecommendDataCallback) {
        //移除view层回调接口
        viewCallbackList.remove(callback)
        //this.callback = null
    }
}