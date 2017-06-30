package com.sugar.grapecollege.account.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 11:03
 * @Description
 */

public class LoginFragment extends QsFragment {
    @Override public int layoutId() {
        return R.layout.fragment_login;
    }

    @Override public void initData(Bundle bundle) {

    }

    public static Fragment getInstance() {
        return new LoginFragment();
    }
}
