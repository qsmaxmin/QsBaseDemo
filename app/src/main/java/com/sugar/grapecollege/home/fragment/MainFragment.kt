package com.sugar.grapecollege.home.fragment

import android.os.Bundle
import android.view.View
import com.qsmaxmin.annotation.bind.Bind
import com.qsmaxmin.annotation.bind.OnClick
import com.qsmaxmin.annotation.event.Subscribe
import com.qsmaxmin.annotation.presenter.Presenter
import com.qsmaxmin.annotation.thread.ThreadPoint
import com.qsmaxmin.annotation.thread.ThreadType
import com.qsmaxmin.qsbase.common.log.L
import com.qsmaxmin.qsbase.common.widget.toast.QsToast
import com.qsmaxmin.qsbase.common.widget.viewpager.autoscroll.AutoScrollViewPager
import com.qsmaxmin.qsbase.common.widget.viewpager.autoscroll.CirclePageIndicator
import com.qsmaxmin.qsbase.common.widget.viewpager.autoscroll.InfinitePagerAdapter
import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem
import com.sugar.grapecollege.R
import com.sugar.grapecollege.common.base.fragment.BasePullListFragment
import com.sugar.grapecollege.common.event.ApplicationEvent.TestClickEvent
import com.sugar.grapecollege.common.http.resp.ModelHomeHeader
import com.sugar.grapecollege.common.http.resp.ModelProductInfo.ProductInfo
import com.sugar.grapecollege.home.adapter.MainListAdapterItem
import com.sugar.grapecollege.home.presenter.MainPresenter
import com.sugar.grapecollege.test.TestABActivity
import com.sugar.grapecollege.test.TestActivity
import kotlinx.android.synthetic.main.header_main_fragment.*

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/27 15:58
 * @Description
 */
@Presenter(MainPresenter::class)
class MainFragment : BasePullListFragment<MainPresenter?, ProductInfo?>() {

    override fun getHeaderLayout(): Int {
        return R.layout.header_main_fragment
    }

    override fun getListAdapterItem(i: Int): QsListAdapterItem<ProductInfo?> {
        return MainListAdapterItem()
    }

    override fun onRefresh() {
        presenter!!.requestListData(false, false)
    }

    override fun onLoad() {
        presenter!!.requestListData(true, false)
    }

    override fun initData(bundle: Bundle?) {
        presenter!!.requestBannerData()
        presenter!!.requestListData(false, true)
    }

    @ThreadPoint(ThreadType.MAIN)
    fun updateHeader(header: ModelHomeHeader) {
        L.i(initTag(), "updateHeader 当前线程:" + Thread.currentThread().name)
        val adapter = InfinitePagerAdapter()
        adapter.setOnPageClickListener { position -> QsToast.show("click:$position") }
        adapter.setData(header.data)
        banner.adapter = adapter
        page_indicator.setViewPager(banner)
        showContentView()
    }

    override fun onResume() {
        super.onResume()
        banner!!.startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        banner!!.stopAutoScroll()
    }

    @OnClick(R.id.tv_left, R.id.tv_right)
    override fun onViewClick(view: View) {
        when (view.id) {
            R.id.tv_left -> intent2Activity(TestActivity::class.java)
            R.id.tv_right -> intent2Activity(TestABActivity::class.java)
        }
    }

    /**
     * eventBus事件接收
     */
    @Subscribe
    fun onEvent(event: TestClickEvent?) {
        intent2Activity(TestActivity::class.java)
    }
}