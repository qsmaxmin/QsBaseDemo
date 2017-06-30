package com.sugar.grapecollege.pay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.sugar.grapecollege.R;

/**
 * @CreateBy zhang
 * @Date 2017/5/10 10:31
 * @Description
 */
public class PayFragment extends QsFragment {


    public static Fragment getInstance(Bundle bundle) {
        PayFragment fragment = new PayFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override public int layoutId() {
        return R.layout.fragment_pay;
    }


    @Override public void onActionBar() {
        super.onActionBar();
        setActivityTitle("支付中心");
    }

    @Override public void initData(Bundle savedInstanceState) {
    }


    @Override public boolean isOpenViewState() {
        return false;
    }
}

