package com.sugar.grapecollege.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.common.viewbind.annotation.OnClick;
import com.qsmaxmin.qsbase.common.widget.toast.QsToast;
import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.dialog.CustomDialog;
import com.sugar.grapecollege.common.event.ApplicationEvent;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 9:41
 * @Description
 */

public class UserFragment extends QsFragment {

    @Override public int layoutId() {
        return R.layout.fragment_user;
    }

    @Override public void onActionBar() {
        setActivityTitle("mine");
    }

    @Override public void initData(Bundle savedInstanceState) {
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
