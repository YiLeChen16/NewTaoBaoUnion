package com.yl.newtaobaounion.ui.adapter

import android.content.Context
import android.content.Intent
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
import com.yl.newtaobaounion.ui.activity.TicketActivity


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

    //重写方法使可获得每个item的位置
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //给条目绑定数据
        //使用Glide类来根据服务器中请求下来的网络图片URL加载网络图片
        var url= data[position].cover
        if(!url.startsWith("https:")){
            url = "https:${url}"
        }
        Glide.with(context)
            .asBitmap()
            .load(url)
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

   inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView
        var normalPrice: TextView
        var ticketPrice: TextView
        var cheap: TextView

        init {
            image = itemView.findViewById(iv_goods_img)
            title = itemView.findViewById(R.id.tv_title)
            normalPrice = itemView.findViewById(R.id.tv_normal_price)
            ticketPrice = itemView.findViewById(R.id.tv_price1)
            cheap = itemView.findViewById(R.id.tv_cheap)
            //设置中划线
            normalPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            //设置item点击跳转事件
            itemView.setOnClickListener {
                val intent = Intent(context, TicketActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("url",data[adapterPosition].couponShareUrl)
                context.startActivity(intent)
            }
        }

    }
}