package com.sugar.grapecollege.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsmaxmin.qsbase.mvvm.MvViewPagerActivity;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.databinding.ActionbarTitleEditBinding;

import androidx.annotation.NonNull;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:46
 * @Description
 */
public abstract class BaseViewPagerActivity extends MvViewPagerActivity {

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

    /**
     * 状态布局切换时，执行的动画
     */
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }

    /**
     * 状态布局切换时，执行的动画
     */
    @Override public int viewStateOutAnimationId() {
        return super.viewStateOutAnimationId();
    }
}
