package com.sugar.grapecollege.product;

import android.os.Bundle;

import com.qsmaxmin.qsbase.mvp.QsABActivity;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/8 16:26
 * @Description
 */

public class ProductDetailActivity extends QsABActivity {

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_title_back;
    }

    @Override public void initData(Bundle bundle) {

    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }
}
