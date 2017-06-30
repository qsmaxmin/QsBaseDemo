package com.sugar.grapecollege.account;

import android.os.Bundle;

import com.qsmaxmin.qsbase.mvp.QsActivity;
import com.sugar.grapecollege.account.fragment.LoginFragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 11:02
 * @Description 用户账户相关
 */

public class AccountActivity extends QsActivity {
    @Override public void initData(Bundle savedInstanceState) {
        commitFragment(LoginFragment.getInstance());
    }

}
