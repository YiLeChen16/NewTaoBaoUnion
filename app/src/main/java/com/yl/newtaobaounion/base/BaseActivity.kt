package com.yl.newtaobaounion.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


/**
 * Activity基类
 */
abstract class BaseActivity<T:ViewBinding>:AppCompatActivity() {
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        binding = method.invoke(null, layoutInflater) as T
        setContentView(binding.root)
        initView()
        initListener()
        initPresenter()
    }

    /**
     * 初始化界面
     */
    abstract fun initView()

    /**
     * 初始化空间的监听事件
     */
    abstract fun initListener()


    /**
     * 初始化presenter
     */
    abstract fun initPresenter()



    /**
     * 释放资源
     * 不强制子类重写，可根据需求重写此方法释放所用资源
     */
    open fun release(){}

    override fun onDestroy() {
        super.onDestroy()
        release()
    }
}