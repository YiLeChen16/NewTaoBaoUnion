package com.yl.newtaobaounion.ui.activity

import android.webkit.WebViewClient
import com.yl.newtaobaounion.base.BaseActivity
import com.yl.newtaobaounion.databinding.ActivityTicketBinding
//领券界面
class TicketActivity:BaseActivity<ActivityTicketBinding>() {

    override fun initView() {
        //获取跳转过来的数据
        val url = intent.getStringExtra("url")
        //装载数据
        if(url!=null){
            binding.webView.webViewClient = WebViewClient()
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl("https:$url")
        }
    }

    override fun initListener() {

    }

    override fun initPresenter() {

    }
}