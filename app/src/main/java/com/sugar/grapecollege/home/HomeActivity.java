package com.sugar.grapecollege.home;

import android.os.Bundle;
import android.widget.TextView;

import com.qsmaxmin.qsbase.mvp.QsABActivity;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.home.fragment.MainFragment;

import butterknife.Bind;

public class HomeActivity extends QsABActivity {

    @Bind(R.id.tv_title) TextView tv_title;

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_title;
    }

    @Override public void initData(Bundle bundle) {
        commitFragment(new MainFragment());
    }

    @Override public boolean isDelayData() {
        return false;
    }
}
