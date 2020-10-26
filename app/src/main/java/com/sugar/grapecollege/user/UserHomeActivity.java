package com.sugar.grapecollege.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.annotation.bind.Bind;
import com.qsmaxmin.annotation.bind.OnClick;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.BaseActivity;
import com.sugar.grapecollege.common.event.UserEvent;
import com.sugar.grapecollege.common.model.UserConfig;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/10/26 14:27
 * @Description
 */
public class UserHomeActivity extends BaseActivity {
    @Bind(R.id.tv_title) TextView tv_title;

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_title_edit;
    }

    @Override public int layoutId() {
        return R.layout.activity_user_home;
    }

    @Override public void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.view_back, R.id.bt_logout})
    @Override public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.view_back:
                activityFinish();
                break;
            case R.id.bt_logout:
                UserConfig.logout();
                activityFinish();
                QsHelper.eventPost(new UserEvent.OnLogout());
                break;
        }
    }
}
