package com.sugar.grapecollege.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsmaxmin.annotation.bind.BindBundle;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.common.widget.toast.QsToast;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.aspect.LoginAspect;
import com.sugar.grapecollege.common.base.BaseActivity;
import com.sugar.grapecollege.common.event.UserEvent;
import com.sugar.grapecollege.common.model.UserConfig;
import com.sugar.grapecollege.databinding.ActivityLoginBinding;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/10/26 14:33
 * @Description
 */
public class LoginActivity extends BaseActivity {
    public static final          String               BK_REQUEST_CODE = "bk_request_code";
    @BindBundle(BK_REQUEST_CODE) int                  requestCode;
    private                      ActivityLoginBinding binding;

    @Override public View onCreateContentView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_login, parent, true);
        binding.setOwner(this);
        return binding.getRoot();
    }

    @Override public void initData(Bundle savedInstanceState) {

    }

    @Override public void onViewClick(@NotNull View view) {
        if (view == binding.btLogin) {
            String text = binding.etUserName.getText().toString();
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
}
