package com.sugar.grapecollege.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qsmaxmin.annotation.bind.Bind;
import com.qsmaxmin.annotation.bind.BindBundle;
import com.qsmaxmin.annotation.bind.OnClick;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.common.widget.toast.QsToast;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.aspect.LoginAspect;
import com.sugar.grapecollege.common.base.BaseActivity;
import com.sugar.grapecollege.common.event.UserEvent;
import com.sugar.grapecollege.common.model.UserConfig;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/10/26 14:33
 * @Description
 */
public class LoginActivity extends BaseActivity {
    public static final String BK_REQUEST_CODE = "bk_request_code";
    @BindBundle(BK_REQUEST_CODE) int requestCode;
    @Bind(R.id.et_user_name) EditText et_user_name;

    @Override public int layoutId() {
        return R.layout.activity_login;
    }

    @Override public void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.bt_login)
    @Override public void onViewClick(View view) {
        String text = et_user_name.getText().toString();
        if (TextUtils.isEmpty(text)) {
            QsToast.show("请输入用户名");
        } else {
            UserConfig.login(text);
            activityFinish();
            LoginAspect.onLogin(requestCode);
            QsHelper.eventPost(new UserEvent.OnLogin());
        }
    }
}
