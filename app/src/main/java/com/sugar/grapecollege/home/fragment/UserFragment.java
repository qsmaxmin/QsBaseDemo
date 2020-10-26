package com.sugar.grapecollege.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.annotation.aspect.QsAspect;
import com.qsmaxmin.annotation.bind.Bind;
import com.qsmaxmin.annotation.bind.BindBundle;
import com.qsmaxmin.annotation.bind.OnClick;
import com.qsmaxmin.annotation.event.Subscribe;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.aspect.LoginAspect;
import com.sugar.grapecollege.common.dialog.CustomDialog;
import com.sugar.grapecollege.common.event.UserEvent;
import com.sugar.grapecollege.common.model.UserConfig;
import com.sugar.grapecollege.home.model.HomeConstants;
import com.sugar.grapecollege.user.UserHomeActivity;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 9:41
 * @Description
 */

public class UserFragment extends QsFragment {
    @Bind(R.id.tv_name)                 TextView tv_name;
    @Bind(R.id.tv_download)             TextView tv_download;
    @Bind(R.id.tv_law)                  TextView tv_law;
    @BindBundle(HomeConstants.BK_TEST)  String   testBindBundle;
    @BindBundle(HomeConstants.BK_TEST2) int      testBindBundle2;

    @Override public int layoutId() {
        return R.layout.fragment_user;
    }

    @Override public void initData(Bundle savedInstanceState) {
        if (UserConfig.getInstance().isLogin()) {
            tv_name.setText(UserConfig.getInstance().userName);
        } else {
            tv_name.setText("点击登录");
        }
        L.i(initTag(), "initData......testBindBundle:" + testBindBundle);
        L.i(initTag(), "initData......testBindBundle2:" + testBindBundle2);
    }

    @OnClick({R.id.tv_download, R.id.tv_myfont, R.id.ll_header}) public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_header:
                goUserCenterBeforeLogin();
                break;
            case R.id.tv_myfont:
                QsHelper.commitDialogFragment(new CustomDialog());
                break;
            case R.id.tv_download:
                break;
        }
    }

    @QsAspect(LoginAspect.class)
    private void goUserCenterBeforeLogin() {
        intent2Activity(UserHomeActivity.class);
    }

    @Subscribe
    public void onEvent(UserEvent.OnLogin event) {
        tv_name.setText(UserConfig.getInstance().userName);
    }

    @Subscribe
    public void onEvent(UserEvent.OnLogout event) {
        tv_name.setText("点击登录");
    }

    @Override public boolean isOpenViewState() {
        return false;
    }
}
