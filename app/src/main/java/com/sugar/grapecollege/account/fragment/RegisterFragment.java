package com.sugar.grapecollege.account.fragment;

import android.os.Bundle;

import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.sugar.grapecollege.R;

/**
 * @CreateBy zhang
 * @Date 2017/5/8 9:15
 * @Description
 */

public class RegisterFragment extends QsFragment {
    @Override public int layoutId() {
        return R.layout.fragment_register;
    }

    @Override public void initData(Bundle bundle) {

    }

    public static RegisterFragment getInstance(){
        return new RegisterFragment();
    }
}
