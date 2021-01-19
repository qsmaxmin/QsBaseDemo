package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;

import com.qsmaxmin.qsbase.mvvm.model.MvModelPager;
import com.sugar.grapecollege.common.base.fragment.BaseViewPagerFragment;

import androidx.fragment.app.Fragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 17:01
 * @Description
 */

public class TestViewPagerFragment extends BaseViewPagerFragment {

    public static Fragment getInstance() {
        return new TestViewPagerFragment();
    }

    @Override public MvModelPager[] createModelPagers() {
        MvModelPager modelPager1 = new MvModelPager();
        modelPager1.fragment = TestFragment.getInstance();
        modelPager1.title = "TestFragment0";
        modelPager1.position = 0;

        MvModelPager modelPager2 = new MvModelPager();
        modelPager2.fragment = TestFragment.getInstance();
        modelPager2.title = "TestFragment1";
        modelPager2.position = 1;

        MvModelPager modelPager3 = new MvModelPager();
        modelPager3.fragment = TestFragment.getInstance();
        modelPager3.title = "TestFragment2";
        modelPager3.position = 2;
        return new MvModelPager[]{modelPager1, modelPager2, modelPager3};
    }

    @Override public void initData(Bundle savedInstanceState) {
        //custom your logic
    }

    @Override public boolean isOpenViewState() {
        return false;
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
    }

    @Override public boolean interceptBackPressed() {
        return true;
    }
}
