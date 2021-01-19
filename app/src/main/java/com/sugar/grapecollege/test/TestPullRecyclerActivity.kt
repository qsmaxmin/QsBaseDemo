package com.sugar.grapecollege.test

import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qsmaxmin.annotation.bind.OnClick
import com.qsmaxmin.annotation.thread.ThreadPoint
import com.qsmaxmin.annotation.thread.ThreadType
import com.qsmaxmin.qsbase.mvvm.adapter.MvRecycleAdapterItem
import com.sugar.grapecollege.R
import com.sugar.grapecollege.common.base.BasePullRecyclerActivity
import com.sugar.grapecollege.test.adapter.TestRecyclerAdapter
import com.sugar.grapecollege.test.fragment.TestViewPagerFragment
import com.sugar.grapecollege.test.model.TestModel.TestModelInfo
import kotlinx.android.synthetic.main.actionbar_title_edit.*

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:47
 * @Description
 */
class TestPullRecyclerActivity : BasePullRecyclerActivity<TestModelInfo>() {
    private var loadingCount: Int = 0

    override fun onCreateActionbarView(inflater: LayoutInflater, parent: ViewGroup): View {
        return createDefaultActionbar(inflater, parent).root
    }

    override fun getRecycleAdapterItem(inflater: LayoutInflater, parent: ViewGroup, type: Int): MvRecycleAdapterItem<TestModelInfo> {
        return TestRecyclerAdapter(layoutInflater, parent)
    }

    override fun initData(bundle: Bundle?) {
        tv_edit.text = "BasePullRecyclerActivity"
        requestTestData(false)
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
        }
    }

    override fun onKeyDown(event: KeyEvent, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            tv_edit!!.visibility = View.VISIBLE
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
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