package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;

import com.qsmaxmin.qsbase.mvp.fragment.QsViewPagerFragment;
import com.qsmaxmin.qsbase.mvp.model.QsModelPager;

import androidx.fragment.app.Fragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 17:01
 * @Description
 */

public class TestViewPagerFragment extends QsViewPagerFragment {

    public static Fragment getInstance() {
        return new TestViewPagerFragment();
    }

    @Override public QsModelPager[] getModelPagers() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = TestFragment.getInstance();
        modelPager1.title = "Fragment";
        modelPager1.position = 0;

        QsModelPager modelPager2 = new QsModelPager();
        modelPager2.fragment = TestListFragment.getInstance();
        modelPager2.title = "ListFragment";
        modelPager2.position = 1;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = TestPullListFragment.getInstance();
        modelPager3.title = "PullListFragment";
        modelPager3.position = 2;
        return new QsModelPager[]{modelPager1, modelPager2, modelPager3};
    }


    @Override public void initData(Bundle savedInstanceState) {

    }

    @Override public boolean isOpenViewState() {
        return false;
    }
}
