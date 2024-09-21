package com.yl.newtaobaounion.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.model.dataBean.BannerData
import com.yl.newtaobaounion.utils.LogUtils


/**
 * @description: 自定义轮播图适配器
 * @author YL Chen
 * @date 2024/9/9 20:57
 * @version 1.0
 */
open class ViewPagerAdapter : PagerAdapter() {

    private var mBannerData: MutableList<BannerData> = mutableListOf()
    private var listener: OnItemListener? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        LogUtils.d(this@ViewPagerAdapter, "instantiateItem...")
        val itemView =
            LayoutInflater.from(container.context).inflate(R.layout.item_pager, container, false)
        val iv = itemView.findViewById<ImageView>(R.id.iv)
        if (iv.parent is ViewGroup) {
            (iv.parent as ViewGroup).removeView(iv)
        }
        LogUtils.d(this,"mBannerData.size-->${mBannerData.size}")
        val realPosition: Int = position % mBannerData.size
        LogUtils.d(this,"realPosition-->${realPosition}")

        //设置加载网络图片的大小
        val picUrl: String = mBannerData[realPosition].cover
        LogUtils.d(this@ViewPagerAdapter, "picUrl-->$picUrl")
        Glide.with(container.context).load(picUrl).into(iv)
        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        container.addView(iv)
        return iv
    }


    //提供方法给外界设置数据
    open fun setData(bannerData: MutableList<BannerData>) {
        this.mBannerData.clear()
        this.mBannerData.addAll(bannerData)
        notifyDataSetChanged()
        LogUtils.d(this@ViewPagerAdapter, "setData-->${mBannerData.size}")
    }

    override fun getCount(): Int {
        //无限轮播
        LogUtils.d(this,"mBannerData.size-->${mBannerData.size}")
        //LogUtils.d(this,"getCount-->${if (mBannerData.size == 0) 0 else Integer.MAX_VALUE}")
        return mBannerData.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        LogUtils.d("ViewPagerAdapter", "isViewFromObject-->${view == `object`}")
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    //提供设置监听回调接口的方法
    open fun setOnItemListener(listener: OnItemListener) {
        this.listener = listener
    }


    //定义回调接口
    interface OnItemListener {
        /**
         * 条目被点击
         * @param toUrl String 条目的跳转url
         */
        fun onItemClick(toUrl: String)
    }

}