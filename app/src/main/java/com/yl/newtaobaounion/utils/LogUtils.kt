package com.yl.newtaobaounion.utils

import android.util.Log

//创建LogUtils工具类统一管理日志输出，避免应用发布后日志输出不可控
object LogUtils {
    private const val CURRENT = 4 //目前输出日志等级
    private const val INFO = 4
    private const val DEBUG = 3
    private const val WARN = 2
    private const val ERROR = 1
    fun i(obj: Any, msg: String?) {
        if (CURRENT >= INFO) {
            Log.i(obj.javaClass.name, msg!!)
        }
    }

    fun d(obj: Any, msg: String?) {
        if (CURRENT >= DEBUG) {
            Log.d(obj.javaClass.name, msg!!)
        }
    }

    fun w(obj: Any, msg: String?) {
        if (CURRENT >= WARN) {
            Log.w(obj.javaClass.name, msg!!)
        }
    }

    fun e(obj: Any, msg: String?) {
        if (CURRENT >= ERROR) {
            Log.e(obj.javaClass.name, msg!!)
        }
    }
}