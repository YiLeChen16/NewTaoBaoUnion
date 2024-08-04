package com.yl.newtaobaounion.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yl.newtaobaounion.R
import com.yl.newtaobaounion.utils.LogUtils

//自定义搜索记录和热词推荐控件
class FlowTextLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ViewGroup(context, attrs, defStyleAttr) {
    private val DEFAULT_SPACE = 20f
    private var mItemHorizontalSpace: Float
    private var mItemVerticalSpace: Float
    private var mWidth: Int = 0
    private var mItemHeight: Int = 0
    private var listener: OnItemClickListener? = null

    //数据集合
    private var data: MutableList<String> = mutableListOf()

    init {
        //获取自定义属性
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FlowTextLayoutStyle)
        mItemHorizontalSpace =
            typedArray.getDimension(R.styleable.FlowTextLayoutStyle_horizontal_space, DEFAULT_SPACE)
        mItemVerticalSpace =
            typedArray.getDimension(R.styleable.FlowTextLayoutStyle_vertical_space, DEFAULT_SPACE)
        typedArray.recycle()
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)


    //添加数据和子View
    fun setData(data: List<String>) {
        this.data = data.toMutableList()
        //根据数据量动态创建子View并添加到界面中
        for (s in data) {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.flow_text_view, this, false) as TextView
            view.text = s
            //给每个子View设置监听事件
            view.setOnClickListener {
                //点击将关键字回传到搜索框中
                listener?.onItemClick(view.text.toString())
            }
            addView(view)
        }
    }


    //创建集合用于存储每一行的子View集合
    private var lines: MutableList<MutableList<View>>? = mutableListOf()

    //测量自身和子View
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (childCount == 0) {
            return
        }
        lines?.clear()
        //创建一个集合用于存储一行的子View
        var line: MutableList<View>? = null
        //获取当前控件的宽度
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        LogUtils.d(this, "mWidth==$mWidth")
        LogUtils.d(this, "onMeasure-->$childCount")
        //测量子View的大小
        //确定总共需要多少行
        for (i in 0 until childCount) {
            //获取子View视图
            val itemView = getChildAt(i)
            //测量子View
            measureChild(itemView, widthMeasureSpec, heightMeasureSpec)
            //将子View添加到集合中
            if (line == null || !isCanAdd(itemView, line)) {
                //创建新的一行
                line = mutableListOf<View>()
                line.add(itemView)
                //将此行添加到总的行集合中
                lines?.add(line)
            } else {
                //直接添加
                line.add(itemView)
            }
        }
        //获取控件高度
        //每个item的高度
        mItemHeight = getChildAt(0).measuredHeight
        val mHigh =
            ((lines?.size!! * mItemHeight) + mItemVerticalSpace * (lines?.size?.plus(1)!!)).toInt()
        //测量自身的大小
        setMeasuredDimension(mWidth, mHigh)
    }

    private fun isCanAdd(itemView: View?, line: MutableList<View>): Boolean {
        var totalWidth = 0
        for (view in line) {
            totalWidth += view.measuredWidth
        }
        totalWidth =
            (totalWidth + itemView?.measuredWidth!! + mItemHorizontalSpace * (line.size + 1)).toInt()
        return totalWidth <= mWidth
    }


    //布局，摆放子view
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount == 0) {
            return
        }
        var top = mItemVerticalSpace.toInt()
        for (line in lines!!) {
            var left = mItemHorizontalSpace.toInt()
            for (i in line.indices) {
                //获取子View
                val itemView = line[i]
                //摆放子View
                itemView.layout(
                    left,
                    top,
                    itemView.measuredWidth + left,
                    itemView.measuredHeight + top
                )
                //更新下一个子View要摆放的left
                left = (left + itemView.measuredWidth + mItemHorizontalSpace).toInt()
            }
            //更新新一行的top值
            top = (top + mItemHeight + mItemVerticalSpace).toInt()
        }
    }

    //对外提供设置子View点击监听回调接口
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    //子View点击监听回调接口
    interface OnItemClickListener {
        fun onItemClick(keyword: String)
    }
}