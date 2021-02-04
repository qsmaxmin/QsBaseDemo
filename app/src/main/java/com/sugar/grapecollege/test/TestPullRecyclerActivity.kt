package com.sugar.grapecollege.test

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qsmaxmin.annotation.thread.ThreadPoint
import com.qsmaxmin.annotation.thread.ThreadType
import com.qsmaxmin.qsbase.mvvm.adapter.MvRecycleAdapterItem
import com.sugar.grapecollege.common.base.BasePullRecyclerActivity
import com.sugar.grapecollege.databinding.ActionbarTitleEditBinding
import com.sugar.grapecollege.test.adapter.TestRecyclerAdapter
import com.sugar.grapecollege.test.model.TestModel.TestModelInfo

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:47
 * @Description
 */
class TestPullRecyclerActivity : BasePullRecyclerActivity<TestModelInfo>() {
    private var loadingCount: Int = 0
    lateinit var actionbarBinding: ActionbarTitleEditBinding

    override fun onCreateActionbarView(inflater: LayoutInflater, parent: ViewGroup): View {
        actionbarBinding = createDefaultActionbar(inflater, parent)
        actionbarBinding.tvTitle.text = "BasePullRecyclerActivity"
        return actionbarBinding.root
    }

    override fun getRecycleAdapterItem(inflater: LayoutInflater, parent: ViewGroup, type: Int): MvRecycleAdapterItem<TestModelInfo> {
        return TestRecyclerAdapter(layoutInflater, parent)
    }

    override fun initData(bundle: Bundle?) {
        requestTestData(false)
    }

    override fun isOpenViewState(): Boolean {
        return true;
    }

    @ThreadPoint(ThreadType.HTTP)
    private fun requestTestData(loadingMore: Boolean) {
        SystemClock.sleep(1000)
        val arrayListOf = arrayListOf<TestModelInfo>()
        for (i in 0..10) {
            val testModelInfo = TestModelInfo()
            testModelInfo.testName = "index:" + i
            arrayListOf.add(testModelInfo)
        }

        if (loadingMore) {
            loadingCount++
            if (loadingCount < 3) {
                addData(arrayListOf)
            } else {
                arrayListOf.clear()
                addData(arrayListOf)
            }
        } else {
            loadingCount = 0
            data = arrayListOf
            showContentView()
        }
    }

    override fun isTransparentStatusBar(): Boolean {
        return true
    }

    override fun onRefresh() {
        requestTestData(false)
    }

    override fun onLoad() {
        requestTestData(true)
    }
}