package com.sugar.grapecollege.common.dialog;

import android.view.View;

import com.qsmaxmin.annotation.bind.OnClick;
import com.qsmaxmin.qsbase.common.widget.dialog.QsDialogFragment;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/3 18:29
 * @Description
 */

public class CustomDialog extends QsDialogFragment {

    @Override protected int getDialogTheme() {
        return R.style.BottomTopDialogStyle;
    }

    @Override protected int layoutId() {
        return R.layout.dialog_compatible;
    }

    @Override protected void initData() {
    }

    @OnClick({R.id.view_click, R.id.tv_positive, R.id.tv_neutral, R.id.tv_negative})
    @Override public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.view_click:
                break;
            case R.id.tv_positive:
                break;
            case R.id.tv_neutral:
                break;
            case R.id.tv_negative:
                break;
        }
        dismissAllowingStateLoss();
    }

}
