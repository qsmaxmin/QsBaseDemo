package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.fragment.BaseFragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:59
 * @Description
 */

public class TestFragment extends BaseFragment {

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
