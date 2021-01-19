package com.sugar.grapecollege.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.BaseActivity;
import com.sugar.grapecollege.common.event.UserEvent;
import com.sugar.grapecollege.common.model.UserConfig;
import com.sugar.grapecollege.databinding.ActivityUserHomeBinding;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/10/26 14:27
 * @Description
 */
public class UserHomeActivity extends BaseActivity {

    private ActivityUserHomeBinding binding;

    @Override public void initData(Bundle savedInstanceState) {

    }

    @Override public void onViewClick(@NotNull View view) {
        if (binding.btLogout == view) {
            UserConfig.logout();
            activityFinish();
            QsHelper.eventPost(new UserEvent.OnLogout());
        }
    }

    @Override public View onCreateActionbarView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return createDefaultActionbar(inflater, parent).getRoot();
    }

    @Override public View onCreateContentView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_user_home, parent, true);
        binding.setOwner(this);
        return binding.getRoot();
    }
}
