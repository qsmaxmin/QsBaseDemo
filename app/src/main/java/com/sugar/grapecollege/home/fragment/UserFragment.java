package com.sugar.grapecollege.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsmaxmin.annotation.aspect.QsAspect;
import com.qsmaxmin.annotation.bind.BindBundle;
import com.qsmaxmin.annotation.bind.OnClick;
import com.qsmaxmin.annotation.event.Subscribe;
import com.qsmaxmin.qsbase.common.log.L;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.aspect.LoginAspect;
import com.sugar.grapecollege.common.base.fragment.BaseFragment;
import com.sugar.grapecollege.common.dialog.CustomDialog;
import com.sugar.grapecollege.common.event.UserEvent;
import com.sugar.grapecollege.common.model.UserConfig;
import com.sugar.grapecollege.databinding.FragmentUserBinding;
import com.sugar.grapecollege.home.model.HomeConstants;
import com.sugar.grapecollege.test.TestVideoActivity;
import com.sugar.grapecollege.user.UserHomeActivity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/3 9:41
 * @Description
 */

public class UserFragment extends BaseFragment {
    @BindBundle(HomeConstants.BK_TEST)  String              testBindBundle;
    @BindBundle(HomeConstants.BK_TEST2) int                 testBindBundle2;
    private                             FragmentUserBinding binding;


    @Override public View onCreateContentView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, parent, true);
        binding.setOwner(this);
        return binding.getRoot();
    }

    @Override public void initData(Bundle savedInstanceState) {
        if (UserConfig.getInstance().isLogin()) {
            binding.tvName.setText(UserConfig.getInstance().userName);
        } else {
            binding.tvName.setText("点击登录");
        }
        L.i(initTag(), "initData......testBindBundle:" + testBindBundle);
        L.i(initTag(), "initData......testBindBundle2:" + testBindBundle2);
    }

    public void onViewClick(View view) {
        if (view == binding.llHeader) {
            goUserCenterBeforeLogin("这是参数");
        } else if (view == binding.tvMyfont) {
            new CustomDialog().show(this);
        } else if (view == binding.tvDownload) {
            intent2Activity(TestVideoActivity.class);
        }
    }

    @QsAspect(value = LoginAspect.class, tag = "从UserFragment来的")
    private void goUserCenterBeforeLogin(String test) {
        intent2Activity(UserHomeActivity.class);
    }

    @Subscribe
    public void onEvent(UserEvent.OnLogin event) {
        binding.tvName.setText(UserConfig.getInstance().userName);
    }

    @Subscribe
    public void onEvent(UserEvent.OnLogout event) {
        binding.tvName.setText("点击登录");
    }

    @Override public boolean isOpenViewState() {
        return false;
    }
}
