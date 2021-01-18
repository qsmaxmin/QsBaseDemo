package com.sugar.grapecollege.test;

import android.os.Bundle;

import com.sugar.grapecollege.common.base.BaseActivity;
import com.sugar.grapecollege.test.fragment.TestHeaderViewFragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:38
 * @Description
 */

public class TestActivity extends BaseActivity {

    @Override public void initData(Bundle bundle) {
        commitFragment(TestHeaderViewFragment.getInstance());
    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }
}
