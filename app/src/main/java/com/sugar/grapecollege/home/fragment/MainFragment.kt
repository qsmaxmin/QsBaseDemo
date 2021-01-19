package com.sugar.grapecollege.home.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.qsmaxmin.annotation.event.Subscribe
import com.qsmaxmin.qsbase.common.log.L
import com.qsmaxmin.qsbase.common.widget.toast.QsToast
import com.qsmaxmin.qsbase.common.widget.viewpager.autoscroll.InfinitePagerAdapter
import com.qsmaxmin.qsbase.mvvm.adapter.MvListAdapterItem
import com.sugar.grapecollege.R
import com.sugar.grapecollege.common.base.fragment.BasePullListFragment
import com.sugar.grapecollege.common.event.ApplicationEvent.TestClickEvent
import com.sugar.grapecollege.common.http.resp.ModelHomeHeader
import com.sugar.grapecollege.common.http.resp.ModelProductInfo.ProductInfo
import com.sugar.grapecollege.common.http.resp.ModelProductList
import com.sugar.grapecollege.databinding.HeaderMainFragmentBinding
import com.sugar.grapecollege.home.adapter.MainListAdapterItem
import com.sugar.grapecollege.test.TestHeaderViewPagerActivity
import com.sugar.grapecollege.test.TestPullRecyclerActivity
import kotlinx.android.synthetic.main.header_main_fragment.*
import java.util.*

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/27 15:58
 * @Description
 */
class MainFragment : BasePullListFragment<ProductInfo?>() {
    lateinit var binding: HeaderMainFragmentBinding

    override fun onCreateListHeaderView(inflater: LayoutInflater): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.header_main_fragment, null, false)
        binding.owner = this
        return binding.root
    }

    override fun getListAdapterItem(i: Int): MvListAdapterItem<ProductInfo?> {
        return MainListAdapterItem()
    }

    override fun onRefresh() {
        val testListData = getTestListData()
        data = testListData.list
    }

    override fun onLoad() {
        val testListData = getTestListData()
        addData(testListData.list)
    }

    override fun initData(bundle: Bundle?) {
        val testHeaderData = getTestHeaderData()
        updateHeader(testHeaderData)

        val testListData = getTestListData()
        data = testListData.list
    }


    private fun getTestHeaderData(): ModelHomeHeader {
        val header = ModelHomeHeader()
        header.code = 0
        header.data = ArrayList(5)
        if (context != null) {
            val bitmap = BitmapFactory.decodeResource(context!!.resources, R.mipmap.ic_launcher)
            header.data.add(bitmap)
            header.data.add(bitmap)
            header.data.add(bitmap)
            header.data.add(bitmap)
        }
        return header
    }

    private fun updateHeader(header: ModelHomeHeader) {
        L.i(initTag(), "updateHeader 当前线程:" + Thread.currentThread().name)
        val adapter = InfinitePagerAdapter()
        adapter.setOnPageClickListener { position -> QsToast.show("click:$position") }
        adapter.setData(header.data)
        banner.adapter = adapter
        page_indicator.setViewPager(banner)
        showContentView()
    }

    private fun getTestListData(): ModelProductList {
        val productList = ModelProductList()
        productList.code = 0
        productList.list = ArrayList(20)
        for (i in 0..19) {
            val detail = ProductInfo()
            detail.productName = "哈哈$i"
            productList.list.add(detail)
        }
        return productList
    }

    override fun onResume() {
        super.onResume()
        banner!!.startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        banner!!.stopAutoScroll()
    }

    override fun onViewClick(view: View) {
        when (view) {
            binding.tvLeft -> intent2Activity(TestHeaderViewPagerActivity::class.java)
            binding.tvRight -> intent2Activity(TestPullRecyclerActivity::class.java)
        }
    }

    /**
     * eventBus事件接收
     */
    @Subscribe
    fun onEvent(event: TestClickEvent?) {
        intent2Activity(TestHeaderViewPagerActivity::class.java)
    }
}