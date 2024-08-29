package com.yl.newtaobaounion.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.base.BaseApplication
import com.yl.newtaobaounion.model.dataBean.DataX
import com.yl.newtaobaounion.model.dataBean.GratiaData
import java.text.SimpleDateFormat

class GratiaListAdapter : RecyclerView.Adapter<GratiaListAdapter.MyViewHolder>() {


    private lateinit var data: List<DataX>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_gratia_content, parent, false)
        return MyViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //绑定数据
        Glide.with(BaseApplication.getAppContext())
            .asBitmap()
            .load(data[position].imgList?.get(0)?.imgUrl)
            .error(R.mipmap.ic_launcher)
            .into(holder.image)
        holder.title.text = data[position].title
        holder.advantage.text = data[position].advantage
        val startTime = SimpleDateFormat("yyyy-MM-dd").format(data[position].startTime)
        val endTime = SimpleDateFormat("yyyy-MM-dd").format(data[position].endTime)
        holder.time.text = "$startTime-$endTime"
    }

    //对外提供设置数据的方法
    fun setData(gratiaData: GratiaData) {
        this.data = gratiaData.data
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        var image : ImageView
        var advantage : TextView
        var title : TextView
        var time : TextView
        init {
            image = itemView.findViewById(R.id.iv_goods_img)
            advantage = itemView.findViewById(R.id.tv_adventage)
            title = itemView.findViewById(R.id.tv_title)
            time = itemView.findViewById(R.id.tv_active_time)
        }
    }
}