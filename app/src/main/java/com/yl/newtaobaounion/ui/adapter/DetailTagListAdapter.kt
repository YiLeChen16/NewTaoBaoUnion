package com.yl.newtaobaounion.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.R.id.iv_goods_img
import com.yl.newtaobaounion.moudle.DetailData


class DetailTagListAdapter(val context: Context) :
    RecyclerView.Adapter<DetailTagListAdapter.MyViewHolder>() {
    private var data = mutableListOf<DetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(context)
            .inflate(R.layout.item_home_view_page_content, parent, false)
        return MyViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //给条目绑定数据
        //使用Glide类来根据服务器中请求下来的网络图片URL加载网络图片
        Glide.with(context)
            .asBitmap()
            .load(data[position].cover)
            .error(R.mipmap.ic_launcher)
            .into(holder.image)
        holder.title.text = data[position].title
        holder.normalPrice.text = data[position].zkFinalPrice
        holder.cheap.text = "领券立省￥"+ String.format("%.2f",data[position].couponAmount)
        holder.ticketPrice.text = String.format("%.2f",data[position].zkFinalPrice.toDouble() - data[position].couponAmount)
    }

    //加载数据时设置数据
    fun setData(data: List<DetailData>) {
        this.data.clear()//清空数据
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    //加载更多数据
    fun addData(data: List<DetailData>){
        val currentSize = this.data.size//当前已加载的条目
        this.data.addAll(data)//将加载后的数据加进来
        //刷新数据
        notifyItemRangeChanged(currentSize,data.size)//从当前条目数开始，刷新加载的条目数
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView
        var normalPrice: TextView
        var ticketPrice: TextView
        var cheap: TextView

        init {
            image = itemView.findViewById<ImageView>(iv_goods_img)
            title = itemView.findViewById<TextView>(R.id.tv_title)
            normalPrice = itemView.findViewById<TextView>(R.id.tv_normal_price)
            ticketPrice = itemView.findViewById<TextView>(R.id.tv_ticket_price)
            cheap = itemView.findViewById<TextView>(R.id.tv_tag_cheap)
            //设置中划线
            normalPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            //为条目设置监听事件
            itemView.setOnClickListener {
                //TODO::
                //点击跳转到领券页面
            }
        }

    }
}