package com.yl.newtaobaounion.utils

import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.Glide.init
import com.google.gson.Gson
import com.yl.newtaobaounion.base.BaseApplication

//缓存数据工具类
open class JSONCacheUtils private constructor(){
    private val JSON_CACHE_SP_NAME = "json_cache_sp_name"
    private var mSP: SharedPreferences? = null
    private var mGson: Gson? = null


    init {
        //获取到一个SharePreference对象
        mSP = BaseApplication.getAppContext()
            .getSharedPreferences(JSON_CACHE_SP_NAME, Context.MODE_PRIVATE)
        mGson = Gson()
    }

    companion object{
        //对外提供获取此类单例的方法
        var mJsonCacheUtils: JSONCacheUtils? = null

        fun getInstance(): JSONCacheUtils {
            if (mJsonCacheUtils == null) {
                mJsonCacheUtils = JSONCacheUtils()
            }
            return mJsonCacheUtils!!
        }
    }


    //缓存不带时间的数据
    fun saveCache(key: String?, value: Any?) {
        this.saveCache(key, value, -1L)
    }

    //缓存带时间的数据
    //将传递过来的数据对象，转换为json字符串，再带上时间，
    // 转为带时间的数据对象，再次转为json字符串，再将其存储的SharePreference中
    fun saveCache(key: String?, value: Any?, duration: Long) {
        var duration = duration
        val edit = mSP!!.edit()
        val json = mGson!!.toJson(value)
        //计算当前时间
        if (duration != -1L) {
            duration += System.currentTimeMillis()
        }
        val cacheWithDuration: CacheWithDuration = CacheWithDuration(json, duration)
        val jsonWithDuration = mGson?.toJson(cacheWithDuration)
        edit.putString(key, jsonWithDuration)
        edit.apply()
    }

    //删除指定缓存数据
    fun deleteCache(key: String?) {
        mSP!!.edit().remove(key).apply()
    }

    //获取缓存数据
    fun <T> getCache(key: String?, clazz: Class<T>?): T? {
        val value = mSP!!.getString(key, null) ?: return null
        val cacheWithDuration: CacheWithDuration =
            mGson!!.fromJson(value, CacheWithDuration::class.java)
        val duration: Long = cacheWithDuration.getDuration()
        //判断当前时间
        if (duration != -1L && (duration - System.currentTimeMillis() < 0)) {
            //过期
            return null
        } else {
            //未过期
            val json: String = cacheWithDuration.getJson()
            val result = mGson?.fromJson(json, clazz)
            return result
        }
    }
}