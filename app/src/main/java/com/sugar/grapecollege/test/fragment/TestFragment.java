package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:59
 * @Description
 */

public class TestFragment extends QsFragment {

    public static Fragment getInstance() {
        return new TestFragment();
    }

    @Override public int layoutId() {
        return R.layout.fragment_user;
    }

    @Override public void initData(Bundle bundle) {
        showContentView();
    }
}
