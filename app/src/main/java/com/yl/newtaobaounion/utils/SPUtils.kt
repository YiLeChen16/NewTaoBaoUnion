package com.yl.newtaobaounion.utils

import android.content.Context


/**
 * SharedPreferences工具类
 *
 * @author llw
 */
object SPUtils {
    private const val NAME = "cacheSP"
    fun putBoolean(key: String?, value: Boolean, context: Context) {
        val sp = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String?, defValue: Boolean, context: Context): Boolean {
        val sp = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
        return sp.getBoolean(key, defValue)
    }

    fun putString(key: String?, value: String?, context: Context) {
        val sp = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String?, defValue: String?, context: Context?): String? {
        if (context != null) {
            val sp = context.getSharedPreferences(
                NAME,
                Context.MODE_PRIVATE
            )
            return sp.getString(key, defValue)
        }
        return ""
    }

    fun putInt(key: String?, value: Int, context: Context) {
        val sp = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String?, defValue: Int, context: Context): Int {
        val sp = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
        return sp.getInt(key, defValue)
    }

    fun putLong(key: String?, value: Long, context: Context) {
        val sp = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String?, defValue: Long, context: Context): Long {
        val sp = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
        return sp.getLong(key, defValue)
    }

    fun remove(key: String?, context: Context) {
        val sp = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().remove(key).apply()
    }

}

