package com.sugar.grapecollege.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qsmaxmin.annotation.permission.Permission
import com.qsmaxmin.qsbase.common.log.L
import com.qsmaxmin.qsbase.common.widget.viewpager.PagerSlidingTabStrip
import com.qsmaxmin.qsbase.mvvm.MvViewPagerActivity
import com.qsmaxmin.qsbase.mvvm.adapter.MvTabAdapterItem
import com.qsmaxmin.qsbase.mvvm.model.MvModelPager
import com.sugar.grapecollege.R
import com.sugar.grapecollege.common.model.AppConfig
import com.sugar.grapecollege.home.adapter.HomeTabAdapterItem
import com.sugar.grapecollege.home.fragment.MainFragment
import com.sugar.grapecollege.home.fragment.UserFragment
import com.sugar.grapecollege.home.model.HomeConstants
import kotlinx.android.synthetic.main.actionbar_title.*
import java.util.*

class HomeActivity : MvViewPagerActivity() {

    override fun onCreateActionbarView(inflater: LayoutInflater, parent: ViewGroup): View {
        return inflater.inflate(R.layout.actionbar_title, parent, true)
    }

    @SuppressLint("SetTextI18n")
    override fun initData(bundle: Bundle?) {
        tv_title.text = "custom bind view"
        checkPermissionThenInit()
        testAppConfig()
    }

    /**
     * 测试数据序列化
     * 基于SharedPreferences存储，使用APT生成代码，方便，直观，高效
     */
    private fun testAppConfig() {
        L.i(initTag(), "testString = " + AppConfig.getInstance().toString())
        if (TextUtils.isEmpty(AppConfig.getInstance().testString)) {
            AppConfig.getInstance().testString = "hello world~"
            AppConfig.getInstance().testInt = 1
            AppConfig.getInstance().testLong = 5
            AppConfig.getInstance().testFloat = 6.6f
            AppConfig.getInstance().testBoolean = true
            AppConfig.getInstance().testInteger_ = 11
            AppConfig.getInstance().testLong_ = 55L
            AppConfig.getInstance().testFloat_ = 66.6f
            AppConfig.getInstance().testBoolean_ = true
            AppConfig.getInstance().testSet = HashSet()
            AppConfig.getInstance().testSet.add("1111111")
            AppConfig.getInstance().testSet.add("2222222")
            AppConfig.getInstance().commit()
        }
    }

    override fun createModelPagers(): Array<MvModelPager>? {
        return null
    }

    @Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun checkPermissionThenInit() {
        val modelPager1 = MvModelPager()
        modelPager1.fragment = MainFragment()
        modelPager1.title = "首页"
        modelPager1.position = 0
        val modelPager3 = MvModelPager()
        modelPager3.fragment = UserFragment()
        modelPager3.title = "我的"
        modelPager3.position = 1
        val bundle = Bundle()
        bundle.putString(HomeConstants.BK_TEST, "hello...")
        modelPager3.fragment.arguments = bundle
        initViewPager(arrayOf(modelPager1, modelPager3), 1)
    }

    /**
     * 自定义tab item UI
     * 如果返回null，则使用默认样式
     */
    override fun createTabAdapterItem(position: Int): MvTabAdapterItem {
        return HomeTabAdapterItem(position)
    }

    /**
     * 初始化tab样式
     */
    override fun initTab(tabStrip: PagerSlidingTabStrip) {
        super.initTab(tabStrip)
        tabStrip.indicatorHeight = 0
        tabStrip.shouldExpand = true
        //......
    }

    override fun isTransparentStatusBar(): Boolean {
        return true
    }
}