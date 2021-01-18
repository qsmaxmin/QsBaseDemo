package com.sugar.grapecollege.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qsmaxmin.qsbase.mvvm.MvHeaderViewPagerActivity
import com.qsmaxmin.qsbase.mvvm.model.MvModelPager
import com.sugar.grapecollege.R
import com.sugar.grapecollege.test.fragment.TestListFragment
import com.sugar.grapecollege.test.fragment.TestPullListFragment

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:38
 * @Description
 */
class TestActivity : MvHeaderViewPagerActivity() {

    override fun onCreateHeaderView(inflater: LayoutInflater, parent: ViewGroup): View {
        return inflater.inflate(R.layout.header_main_fragment, parent, true);
    }

    override fun createModelPagers(): Array<MvModelPager>? {
        val modelPager1 = MvModelPager()
        modelPager1.fragment = TestListFragment.getInstance()
        modelPager1.title = "title1"
        modelPager1.position = 0
        val modelPager2 = MvModelPager()
        modelPager2.fragment = TestPullListFragment.getInstance()
        modelPager2.title = "title1"
        modelPager2.position = 0
        return arrayOf(modelPager1, modelPager2)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun isOpenViewState(): Boolean {
        return false
    }

    override fun isTransparentStatusBar(): Boolean {
        return true
    }
}