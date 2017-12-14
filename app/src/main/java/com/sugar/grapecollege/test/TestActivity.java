package com.sugar.grapecollege.test;

import android.os.Bundle;

import com.qsmaxmin.qsbase.mvp.QsActivity;
import com.sugar.grapecollege.test.fragment.TestHeaderViewFragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:38
 * @Description
 */

public class TestActivity extends QsActivity {

    @Override public void initData(Bundle bundle) {
        commitFragment(TestHeaderViewFragment.getInstance());
    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }
}
