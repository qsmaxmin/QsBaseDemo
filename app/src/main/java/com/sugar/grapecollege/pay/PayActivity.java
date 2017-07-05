package com.sugar.grapecollege.pay;

import android.os.Bundle;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.common.viewbind.annotation.OnClick;
import com.qsmaxmin.qsbase.mvp.QsABActivity;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.pay.fragment.PayFragment;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/6 11:33
 * @Description 分发支付
 */

public class PayActivity extends QsABActivity {
    @Bind(R.id.tv_title) TextView tv_title;

    @Override public void initData(Bundle savedInstanceState) {
        commitFragment(PayFragment.getInstance(getIntent().getExtras()));
    }

    @Override public boolean isDelayData() {
        return false;
    }

    @OnClick(R.id.ll_back) public void onItemViewClick() {
        onBackPressed();
    }

    @Override public int actionbarLayoutId() {
        return 0;
    }
}
