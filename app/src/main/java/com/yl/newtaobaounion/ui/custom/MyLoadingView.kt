package com.yl.newtaobaounion.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.*
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.base.BaseFragment
import com.yl.newtaobaounion.utils.LogUtils

//自定义正在加载控件
class MyLoadingView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context,attrs){
    constructor(context: Context) : this(context,null)
    constructor(context: Context,attrs: AttributeSet?,defStyleAttr:Int) : this(context,attrs)

    private var mDegrees:Float = 10.0f//初始旋转角度

    //init相当于调用了super
    init {
        //设置加载图片
        setImageResource(R.mipmap.loading)
    }

    override fun onDraw(canvas: Canvas?) {
        //绕中心旋转图片，达到加载的效果(必须在super之前调用才有效果)
        LogUtils.d(this@MyLoadingView, "onDraw...$mDegrees")
        canvas?.rotate(mDegrees, (width / 2).toFloat(), (height /2).toFloat())
        super.onDraw(canvas)
    }

    fun startRotate() {
        //注册监听BaseFragment的currentState

        //开启子线程
        //让加载圈旋转
        post(object : Runnable {
            override fun run() {
                mDegrees = (mDegrees + 10) % 360 // 循环旋转
                //刷新界面
                invalidate()
                LogUtils.d(this@MyLoadingView, "Loading...$mDegrees")
                //如果当前fragment的状态不是加载状态，则停止旋转
                if (BaseFragment.getFragmentCurrentState() != BaseFragment.State.LOADING && BaseFragment.getFragmentCurrentState() != BaseFragment.State.NONE) {
                    LogUtils.d(this@MyLoadingView,"BaseFragment.getFragmentCurrentState()-->${BaseFragment.getFragmentCurrentState()}")
                    //停止旋转
                    removeCallbacks(this)
                    LogUtils.d(this@MyLoadingView, "removeCallbacks...")
                } else {
                    postDelayed(this, 20)
                }
            }
        })
    }


}