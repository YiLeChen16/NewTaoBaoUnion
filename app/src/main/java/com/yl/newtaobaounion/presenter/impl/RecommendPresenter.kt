package com.yl.newtaobaounion.presenter.impl

import com.yl.newtaobaounion.model.RecommendModel
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.presenter.IRecommendPresenter
import com.yl.newtaobaounion.view.IRecommendDataCallback


//私有化构造方法
//由于这个presenter会被首页的多个fragment使用，故直接私有化构造方法，并对外提供单例获取此类的对象，避免重复创建对象
class RecommendPresenter private constructor() : IRecommendPresenter {

    //提供获取此类对象的方法
    companion object {
        private var recommendPresenter: RecommendPresenter = RecommendPresenter()
        fun getInstance(): RecommendPresenter {
            return recommendPresenter
        }
    }

    //创建一个集合用于存储注册过来的view层通知接口
    private var viewCallbackList: MutableSet<IRecommendDataCallback> = mutableSetOf()

    //加载第一页推荐数据
    override fun getRecommendDataByKeyWord(viewKeyword: String, keyword: String, refresh: Boolean) {
        //根据当前页面的关键字确定需要通知到哪个页面
        if (!refresh) {
            onDataLoadingByViewKeyword(viewKeyword)
        }
        RecommendModel.getRecommendByKeyWord(
            keyword = keyword,
            //数据请求成功回调
            successCallBack = { recommendBean ->
                //通知View层，数据请求成功
                if (refresh) {
                    onReDataSuccessByViewKeyword(viewKeyword, recommendBean)
                } else {
                    onDataSuccessByViewKeyword(viewKeyword, recommendBean)
                }
            },
            emptyCallBack = {
                //通知View层，数据为空
                if (refresh) {
                    onReDataEmptyByViewKeyword(viewKeyword)
                } else {
                    onDataEmptyByViewKeyword(viewKeyword)
                }
            },
            errorCallBack = {
                //通知View层，数据请求错误
                if (refresh) {
                    onReDataErrorByViewKeyword(viewKeyword)
                } else {
                    onDataErrorByViewKeyword(viewKeyword)
                }
            }
        )
    }

    //加载更多数据
    override fun loadMoreRecommendDataByKeyWord(viewKeyword: String, keyword: String) {
        RecommendModel.getMoreRecommendByKeyWord(
            viewKeyword = viewKeyword,
            keyword = keyword,
            //通知view层，数据请求成功
            successCallBack = {
                loadMoreRecommendBean->
                onMoreDataSuccessByViewKeyword(viewKeyword, loadMoreRecommendBean)
            },
            //通知View层，数据为空
            emptyCallBack = {
                onMoreDataEmptyByViewKeyword(viewKeyword)
            },
            //通知View层，数据请求失败
            errorCallBack = {
                onMoreDataErrorByViewKeyword(viewKeyword)
            }
        )
    }

    //刷新数据，直接回调之前请求数据的方法即可
    override fun reLoadRecommendDataByKeyWord(viewKeyword: String, keyword: String) {
        //refresh标志为true，表示为刷新的数据
        getRecommendDataByKeyWord(viewKeyword, keyword, true)
    }

    /**
     * 回调view层接口函数
     */

    //刷新成功
    private fun onReDataSuccessByViewKeyword(viewKeyword: String, recommendBean: RecommendBean) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onRefreshDataLoadSuccess(recommendBean)
            }
        }
    }

    //刷新失败
    private fun onReDataErrorByViewKeyword(viewKeyword: String) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onRefreshDataLoadError()
            }
        }
    }

    //刷新为空
    private fun onReDataEmptyByViewKeyword(viewKeyword: String) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onRefreshDataLoadEmpty()
            }
        }
    }

    //根据当前页面的关键字确定需要通知到哪个页面，通知对应View层数据正在加载中
    private fun onDataLoadingByViewKeyword(viewKeyword: String) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onLoading()
            }
        }
    }

    //根据当前页面的关键字确定需要通知到哪个页面，通知对应View层数据加载错误
    private fun onDataErrorByViewKeyword(viewKeyword: String) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onError()
            }
        }
    }

    //根据当前页面的关键字确定需要通知到哪个页面，通知对应View层数据加载成功
    private fun onDataSuccessByViewKeyword(viewKeyword: String, recommendBean: RecommendBean) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onRecommendDataLoad(recommendBean)
            }
        }
    }

    //根据当前页面的关键字确定需要通知到哪个页面，通知对应View层数据为空
    private fun onDataEmptyByViewKeyword(viewKeyword: String) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onEmpty()
            }
        }
    }

    //加载更多数据，通知对应View层数据加载成功
    private fun onMoreDataSuccessByViewKeyword(viewKeyword: String, recommendBean: RecommendBean) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onMoreDataLoadSuccess(recommendBean)
            }
        }
    }

    //加载更多数据，通知对应View层数据加载失败
    private fun onMoreDataErrorByViewKeyword(viewKeyword: String) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
                iRecommendDataCallback.onMoreDataLoadError()
            }
        }
    }

    //加载更多数据，通知对应View层数据加载为空
    private fun onMoreDataEmptyByViewKeyword(viewKeyword: String) {
        //遍历viewCallback集合，找到对应关键字的viewCallback进行通知
        for (iRecommendDataCallback in viewCallbackList) {
            if (iRecommendDataCallback.getViewKeyWord() == viewKeyword) {
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