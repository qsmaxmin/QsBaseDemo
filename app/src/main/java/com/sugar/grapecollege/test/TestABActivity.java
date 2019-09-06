package com.sugar.grapecollege.test;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.common.viewbind.annotation.OnClick;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.BaseABActivity;
import com.sugar.grapecollege.test.fragment.TestPullRecyclerFragment;
import com.sugar.grapecollege.test.fragment.TestViewPagerFragment;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:47
 * @Description
 */

public class TestABActivity extends BaseABActivity {
    @Bind(R.id.tv_edit) TextView tv_edit;

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_title_edit;
    }

    @Override public void initData(Bundle bundle) {
        tv_edit.setText("ViewPager");
        commitFragment(TestPullRecyclerFragment.getInstance());
    }

    @OnClick({R.id.tv_edit, R.id.view_back}) public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                tv_edit.setVisibility(View.GONE);
                commitBackStackFragment(TestViewPagerFragment.getInstance());
                break;
            case R.id.view_back:
                tv_edit.setVisibility(View.VISIBLE);
                onBackPressed();
                break;
        }
    }

    @Override public boolean onKeyDown( KeyEvent event,int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            tv_edit.setVisibility(View.VISIBLE);
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }
}
