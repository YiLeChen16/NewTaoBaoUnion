package com.yl.newtaobaounion.ui.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.tencent.mmkv.MMKV
import com.yl.newtaobaounion.base.BaseApplication
import com.yl.newtaobaounion.base.BaseFragment
import com.yl.newtaobaounion.databinding.FragmentHomeViewPagerBaseBinding
import com.yl.newtaobaounion.databinding.FragmentHomeViewPagerBinding
import com.yl.newtaobaounion.model.dataBean.CategoriesData
import com.yl.newtaobaounion.model.dataBean.RecommendBean
import com.yl.newtaobaounion.presenter.impl.RecommendPresenter
import com.yl.newtaobaounion.ui.adapter.DetailTagListAdapter
import com.yl.newtaobaounion.utils.LogUtils
import com.yl.newtaobaounion.utils.PresenterManager
import com.yl.newtaobaounion.utils.SPUtils
import com.yl.newtaobaounion.utils.ToastUtils
import com.yl.newtaobaounion.view.IRecommendDataCallback


//首页viewPager的Fragment
class HomeViewPagerFragment: BaseFragment(),
    IRecommendDataCallback {

        companion object {
            //参数传递
            //使用fragment必须提供一个无参构造，
            // 因为当Fragment因为某种原因重新创建时，会调用到onCreate方法传入之前保存的状态，
            // 在instantiate方法中通过反射无参构造函数创建一个Fragment，并且为Arguments初始化为原来保存的值，
            // 而此时如果没有无参构造函数就会抛出异常，造成程序崩溃。
            fun newInstance(categoriesData: CategoriesData?): HomeViewPagerFragment {
                val args = Bundle()
                args.putParcelable("categoriesData", categoriesData)
                val fragment = HomeViewPagerFragment()
                fragment.setArguments(args)
                return fragment
            }
        }




    private lateinit var binding: FragmentHomeViewPagerBinding
    var firstTextView: TextView? = null
    var currentTextView: TextView? = null
    private lateinit var recommendPresenter: RecommendPresenter
    private lateinit var viewKeyWord: String
    private var detailTagListAdapter: DetailTagListAdapter? = null
    val mmkv = MMKV.defaultMMKV()


    //创建集合用于保存当前页面的所有Tag的对象
    private var tagList: MutableSet<TextView> = mutableSetOf()

    //标志第一次加载
    var firstLaunch: Boolean = true

    // 获取定义在color文件中的颜色
    private var orange: Int? = 0

    override fun loadData() {

        //默认加载第一个tag的推荐内容
        //SPUtils.putString(viewKeyWord,firstTextView?.text.toString(),BaseApplication.getBaseApplicationContext())
        //将refresh标志为false，表示此次不是刷新的数据
        recommendPresenter.getRecommendDataByKeyWord(
            viewKeyWord,
            firstTextView?.text.toString(),
            false
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun loadRootViewBinding(container: ViewGroup?): ViewBinding {
        //获取传递的参数
        val categoriesData = arguments?.getParcelable<CategoriesData>("categoriesData")
        orange = ContextCompat.getColor(
            BaseApplication.getAppContext(),
            com.yl.newtaobaounion.R.color.orange
        )
        val baseLayoutBinding = FragmentHomeViewPagerBaseBinding.inflate(layoutInflater)
        //保存当前页面的分类为页面关键字
        viewKeyWord = categoriesData?.word!!
        //设置为四列
        baseLayoutBinding.gridLayout.columnCount = 4
        // 用于存储每行的最大宽度和最大高度
        val maxWidths = mutableListOf<Int>()
        val maxHeights = mutableListOf<Int>()
        //动态将具体分类数据设置到GridLayout中
        for ((index, detailCategories) in categoriesData.subCategoryList.withIndex()) {
            val textView = TextView(context)
            textView.text = detailCategories.word
            textView.setTextColor(Color.BLACK)
            textView.gravity = Gravity.CENTER
            textView.setPadding(10, 10, 10, 10)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textView.isClickable = true
            textView.isFocusableInTouchMode = true
            //设置焦点监听事件
            textView.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                LogUtils.d(
                    this@HomeViewPagerFragment,
                    "${textView.text} hasFocus-->$hasFocus"
                )
                if (hasFocus) {
                    if (textView != currentTextView) {
                        currentTextView?.setTextColor(Color.BLACK)
                    }
                    // 当前 TextView 获得焦点时执行的操作
                    textView.setTextColor(orange!!) // 示例：改变文本颜色为橙色
                    //点击了其他的tag，切换当前页面关键字
                    LogUtils.e(this@HomeViewPagerFragment, "${textView.text.toString()} 加载数据")
                    recommendPresenter.getRecommendDataByKeyWord(
                        viewKeyWord,
                        textView.text.toString(),
                        false
                    )
                    //记录当前点击的tag
                    currentTextView = textView
                    //将当前点击的tag保存到SP中
                    /*SPUtils.putString(
                        viewKeyWord,
                        textView.text.toString(),
                        BaseApplication.getAppContext()
                    )*/
                    //用MMVK替代SP
                    mmkv.encode(viewKeyWord,textView.text.toString())
                } else {
                    // 当前 TextView 失去焦点时执行的操作
                    textView.setTextColor(Color.BLACK) // 示例：恢复文本颜色为黑色
                }
            }

            //保留第一个textView,并选中第一个textView
            if (index == 0) {
                firstTextView = textView
                currentTextView = firstTextView
                textView.setTextColor(orange!!)
            }
            // 创建GridLayout的布局参数
            val params = GridLayout.LayoutParams()

            // 测量宽度
            textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val width = textView.measuredWidth
            val height = textView.measuredHeight

            // 记录每行的最大宽度和最大高度
            if (maxWidths.size <= index / 4) {
                maxWidths.add(width)
            } else {
                maxWidths[index / 4] = maxOf(maxWidths[index / 4], width)
            }
            if (maxHeights.size <= index / 4) {
                maxHeights.add(height)
            } else {
                maxHeights[index / 4] = maxOf(maxHeights[index / 4], height)
            }

            // 使用GridLayout.Spec来定义行列和跨度
            val columnSpec =
                GridLayout.spec(index % 4, 1, 1f * maxWidths[index / 4]) // 列: 列起始位置, 列跨度, 列权重
            val rowSpec =
                GridLayout.spec(index / 4, 1, 1f * maxHeights[index / 4])   // 行: 行起始位置, 行跨度, 行权重

            // 设置布局参数
            params.columnSpec = columnSpec
            params.rowSpec = rowSpec

            // 应用布局参数到textView
            textView.layoutParams = params
            //保存textView
            tagList.add(textView)
            baseLayoutBinding.gridLayout.addView(textView)
        }
        return baseLayoutBinding
    }

    override fun loadViewBinding(): FragmentHomeViewPagerBinding {
        binding = FragmentHomeViewPagerBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {
        /*//遍历界面所有textView，将当前选中的tag改变字体颜色
        currentTextView?.setTextColor(orange!!)*/
    }

    override fun initPresenter() {
        recommendPresenter = PresenterManager.getInstance().recommendPresenter
        recommendPresenter.registerViewCallback(this)
    }

    override fun initListener() {
        //为刷新框架设置下拉刷新监听事件
        binding.refreshLayout.setOnLoadMoreListener {
            //加载更多条目
            LogUtils.d(
                this@HomeViewPagerFragment,
                "LoadMore currentTag-->${currentTextView?.text.toString()}"
            )
            recommendPresenter.loadMoreRecommendDataByKeyWord(
                viewKeyWord,
                currentTextView?.text.toString()
            )
        }
        //为刷新框架设置上拉刷新监听事件
        binding.refreshLayout.setOnRefreshListener {
            //刷新当前页面
            LogUtils.d(
                this@HomeViewPagerFragment,
                "Refresh currentTag-->${currentTextView?.text.toString()}"
            )
            recommendPresenter.reLoadRecommendDataByKeyWord(
                viewKeyWord,
                currentTextView?.text.toString()
            )
        }
    }

    //点击网络加载错误试图时，触发此方法
    override fun retry() {
        LogUtils.d(this, "retry")
        //重新进行网络请求
        /*val tag = SPUtils.getString(
            viewKeyWord,
            firstTextView?.text.toString(),
            BaseApplication.getAppContext()
        )*/
        val tag = mmkv.decodeString(viewKeyWord, firstTextView?.text.toString())
        recommendPresenter.getRecommendDataByKeyWord(viewKeyWord, tag!!, false)
    }

    override fun onRecommendDataLoad(recommendBean: RecommendBean) {
        setupCurrentState(State.SUCCESS)
        //TODO::装载数据到列表中
        LogUtils.d(this, "recommendBean-->$recommendBean")
        //创建适配器
        detailTagListAdapter = DetailTagListAdapter(requireContext())
        //设置数据
        detailTagListAdapter!!.setData(recommendBean.data.list)
        //给列表设置适配器
        binding.list.layoutManager = GridLayoutManager(context, 2)
        binding.list.adapter = detailTagListAdapter
    }

    /**
     * 加载更多的数据成功
     */
    override fun onMoreDataLoadSuccess(recommendBean: RecommendBean) {
        LogUtils.d(this, "onMoreDataLoadSuccess-->$recommendBean")
        //停止下拉刷新
        binding.refreshLayout.finishLoadMore()
        //设置数据
        detailTagListAdapter?.addData(recommendBean.data.list)
        //提示用户加载成功
        ToastUtils.showToast("已成功加载${recommendBean.data.list.size}个宝贝~")
    }

    /**
     * 加载更多数据失败
     */
    override fun onMoreDataLoadError() {
        LogUtils.d(this, "onMoreDataLoadError--")
        //停止下拉刷新
        binding.refreshLayout.finishLoadMore()
        ToastUtils.showToast("数据好像被外星人抢走咯~请稍后再试~")
    }

    /**
     * 加载更多数据为空
     */
    override fun onMoreDataLoadEmpty() {
        LogUtils.d(this, "onMoreDataLoadEmpty--")
        //停止下拉刷新
        binding.refreshLayout.finishLoadMore()
        ToastUtils.showToast("已到达宇宙的尽头~")
    }

    /**
     * 刷新数据成功
     */
    override fun onRefreshDataLoadSuccess(recommendBean: RecommendBean) {
        LogUtils.d(this, "onRefreshDataLoadSuccess-->$recommendBean")
        //停止刷新加载
        binding.refreshLayout.finishRefresh()
        //TODO::设置数据
        detailTagListAdapter?.setData(recommendBean.data.list)
        ToastUtils.showToast("刷新成功~")
    }

    /**
     * 刷新数据失败
     */
    override fun onRefreshDataLoadError() {
        LogUtils.d(this, "onRefreshDataLoadError--")
        //停止刷新加载
        binding.refreshLayout.finishRefresh()
        //TODO::
        //提示用户刷新失败，并保留当前加载的数据
        ToastUtils.showToast("数据好像被外星人抢走咯~请稍后再试~")
    }

    /**
     * 刷新数据为空
     */
    override fun onRefreshDataLoadEmpty() {
        LogUtils.d(this, "onRefreshDataLoadEmpty--")
        //停止刷新加载
        binding.refreshLayout.finishRefresh()
        //提示用户刷新数据为空，并保留当前加载的数据
        ToastUtils.showToast("数据好像被外星人抢走咯~请稍后再试~")
    }

    /**
     * 返回当前页面的关键词，用于给presenter层通知页面状态到对应页面
     */
    override fun getViewKeyWord(): String {
        return viewKeyWord
    }

    override fun onError() {
        setupCurrentState(State.ERROR)
    }

    override fun onEmpty() {
        setupCurrentState(State.EMPTY)
    }

    override fun onLoading() {
        setupCurrentState(State.LOADING)
    }

    override fun release() {
        //取消注册
        recommendPresenter.unregisterViewCallback(this)
    }


    //当fragment被切换回来后会执行此方法
    //用于记忆用户在当前页面选择的tag
    override fun onResume() {
        //不是第一次加载
        if (!firstLaunch) {
            LogUtils.d(this, "onResume-->重新回到界面")
            //从SP中获取到当前页面的tag
/*            val currentTag =
                SPUtils.getString(
                    viewKeyWord,
                    firstTextView?.text.toString(),
                    BaseApplication.getAppContext()
                )
*/
            //使用MMVK替代SP
            val currentTag = mmkv.decodeString(viewKeyWord,firstTextView?.text.toString())
            //遍历textView集合
            for (textView in tagList) {
                if (textView.text.toString() == currentTag) {
                    textView.requestFocus()
                }
            }
        } else {
            LogUtils.d(this, "onResume-->第一次加载到界面")
            firstLaunch = false
        }
        super.onResume()
    }
}