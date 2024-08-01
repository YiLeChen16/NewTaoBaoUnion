package com.yl.newtaobaounion.base

import android.app.Application
import android.content.Context

open class BaseApplication : Application() {
    companion object {
        private lateinit var context: Context
        //获取context实例
        open fun getBaseApplicationContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = baseContext
    }

}