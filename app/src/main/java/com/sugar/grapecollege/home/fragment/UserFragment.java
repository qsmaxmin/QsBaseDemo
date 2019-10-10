package com.sugar.grapecollege.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.common.viewbind.annotation.BindBundle;
import com.qsmaxmin.qsbase.common.viewbind.annotation.OnClick;
import com.qsmaxmin.qsbase.common.widget.toast.QsToast;
import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.dialog.CustomDialog;
import com.sugar.grapecollege.common.event.ApplicationEvent;
import com.sugar.grapecollege.home.model.HomeConstants;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 9:41
 * @Description
 */

public class UserFragment extends QsFragment {
    @Bind(R.id.tv_name)                TextView tv_name;
    @BindBundle(HomeConstants.BK_TEST) String   testBindBundle;

    @Override public int layoutId() {
        return R.layout.fragment_user;
    }

    @Override public void onActionBar() {
        setActivityTitle("mine");
    }

    @Override public void initData(Bundle savedInstanceState) {
        L.i(initTag(), "initData.......testBindBundle:" + testBindBundle);
        tv_name.setText("AAAAAAA");
    }

    @OnClick({R.id.tv_download, R.id.tv_myfont, R.id.tv_law, R.id.ll_header}) public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_header:
                QsToast.show("测试eventBus");
                QsHelper.eventPost(new ApplicationEvent.TestClickEvent());
                break;
            case R.id.tv_myfont:
                QsHelper.commitDialogFragment(new CustomDialog());
                break;
            case R.id.tv_download:
                break;
            case R.id.tv_law:
                break;
        }
    }

    @Override public boolean isOpenViewState() {
        return false;
    }
}
