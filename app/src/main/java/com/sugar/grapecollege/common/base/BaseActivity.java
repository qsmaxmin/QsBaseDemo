package com.sugar.grapecollege.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsmaxmin.qsbase.mvvm.MvActivity;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.databinding.ActionbarTitleEditBinding;

import androidx.annotation.NonNull;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:23
 * @Description
 */
public abstract class BaseActivity extends MvActivity {
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }

    public ActionbarTitleEditBinding createDefaultActionbar(LayoutInflater inflater, ViewGroup parent) {
        ActionbarTitleEditBinding binding = ActionbarTitleEditBinding.inflate(inflater, parent, true);
        binding.setOwner(this);
        return binding;
    }

    @Override public void onViewClick(@NonNull View view) {
        super.onViewClick(view);
        if (view.getId() == R.id.iv_back) {
            activityFinish();
        }
    }
}
