package com.yl.newtaobaounion.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.databinding.ViewContentEmptyBinding
import com.yl.newtaobaounion.databinding.ViewLoadingBinding
import com.yl.newtaobaounion.databinding.ViewNetErrBinding
import com.yl.newtaobaounion.ui.custom.MyLoadingView
import com.yl.newtaobaounion.utils.LogUtils
import org.w3c.dom.Text

/**
 * Fragment基类
 */
abstract class BaseFragment : Fragment() {

    companion object{
        var currentState: State? = State.NONE
        //获取当前加载状态
        fun getFragmentCurrentState(): State? {
            return currentState
        }
    }

    //lateinit var binding: T
    var mBaseLayout: FrameLayout? = null

    var successView: View? = null
    var errorView: View? = null
    var emptyView: View? = null
    var loadingView: View? = null

    var myLoadingView: MyLoadingView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //加载根布局的ViewBinding
        val loadRootViewBinding = loadRootViewBinding(container)
        mBaseLayout = loadRootViewBinding.root.findViewById(R.id.base_layout)
        //加四种状态全部载入根布局中
        loadStateView(container)
        initView()
        initListener()
        initPresenter()
        loadData()

        return loadRootViewBinding.root
    }

    /**
     * 使用presenter对象请求数据，加载所需数据
     */
    abstract fun loadData()


    /**
     * 加载根布局用于载入加载状态视图
     */
    abstract fun loadRootViewBinding(container: ViewGroup?): ViewBinding

    /**
     * 加载具体布局:即加载成功的布局
     */
    abstract fun loadViewBinding(): ViewBinding

    //将四种加载状态的view全部载入根布局中
    //并设置默认状态
    private fun loadStateView(container: ViewGroup?) {
        successView = loadSuccessView().root
        errorView = loadErrorView().root
        emptyView = loadEmptyView().root
        loadingView = loadLoadingView().root
        //myLoadingView = loadLoadingView().myLoadingView
        mBaseLayout?.addView(successView)
        mBaseLayout?.addView(errorView)
        mBaseLayout?.addView(emptyView)
        mBaseLayout?.addView(loadingView)

        //将状态设置为NONE，全部不可见
        setupCurrentState(State.NONE)

        //设置errorView的监听事件
        onRetry()
    }

    //由于界面的数据都是由网络请求得来的，故为了避免加载未完成或成功时界面一片空白，需定义四种加载状态
    enum class State {
        SUCCESS,//加载成功，将请求到的数据设置到对应的布局并加载布局
        ERROR,//加载失败，向用户展示加载失败的图标提示
        EMPTY,//加载内容为空，向用户展示内容为空的图标提示
        LOADING,//正在加载中，向用户展示加载圈
        NONE//正在加载中，向用户展示加载圈
    }


    //设置当前加载状态
    fun setupCurrentState(currentState: State) {
        BaseFragment.currentState = currentState
        LogUtils.d(this,"setupCurrentState-->$currentState")
        //根据加载状态显示对应的view
        //若为NONE则全部都不显示
        successView!!.visibility =
            if (currentState == State.SUCCESS) View.VISIBLE else View.INVISIBLE
        errorView!!.visibility =
            if (currentState == State.ERROR) View.VISIBLE else View.INVISIBLE
        emptyView!!.visibility =
            if (currentState == State.EMPTY) View.VISIBLE else View.INVISIBLE
        loadingView!!.visibility =
            if (currentState == State.LOADING) View.VISIBLE else View.INVISIBLE
        //若为Loading状态，需手动开启控件
        if (currentState == State.LOADING) {
            //获取loadingView中的加载控件

            myLoadingView = loadingView?.findViewById(R.id.my_loading_view)
            myLoadingView?.startRotate()
        }
    }

    /**
     * 加载成功,布局由子类传入的viewBinding决定
     */
    fun loadSuccessView(): ViewBinding {
        return loadViewBinding()
    }

    /**
     * 加载失败
     */
    fun loadErrorView(): ViewNetErrBinding {
        return ViewNetErrBinding.inflate(layoutInflater)
    }

    /**
     * 加载内容为空
     */
    fun loadEmptyView(): ViewContentEmptyBinding {
        return ViewContentEmptyBinding.inflate(layoutInflater)
    }

    /**
     * 正在加载中
     */
    fun loadLoadingView() :ViewBinding{
        return ViewLoadingBinding.inflate(layoutInflater)
    }





    /**
     * 初始化界面
     */
    abstract fun initView()


    /**
     * 初始化对应的presenter
     */
    abstract fun initPresenter()


    /**
     * 初始化监听事件
     */
    abstract fun initListener()


    /**
     * 实现在网络加载错误时，点击界面，重新加载数据
     */
    fun onRetry(){
        errorView?.setOnClickListener {
            retry()
        }
    }

    /**
     * 由子类实现重新加载的逻辑
     */
    abstract fun retry()


    /**
     * 释放资源
     *
     */
    open fun release() {

    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }
}