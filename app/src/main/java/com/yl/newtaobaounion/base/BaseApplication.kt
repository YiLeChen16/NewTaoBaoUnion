package com.yl.newtaobaounion.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV

open class BaseApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        //获取context实例
        open fun getAppContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        //初始化MMKV
        MMKV.initialize(this)
        context = baseContext
    }

}