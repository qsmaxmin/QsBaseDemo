package com.sugar.grapecollege.common.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsmaxmin.qsbase.common.widget.dialog.QsDialogFragment;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.databinding.DialogCompatibleBinding;

import androidx.databinding.DataBindingUtil;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/3 18:29
 * @Description
 */

public class CustomDialog extends QsDialogFragment {
    private DialogCompatibleBinding binding;

    @Override protected int getDialogTheme() {
        return R.style.BottomTopDialogStyle;
    }

    @Override protected int layoutId() {
        return R.layout.dialog_compatible;
    }

    @Override protected View onCreateContentView(LayoutInflater inflater, ViewGroup parent) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_compatible, parent, true);
        binding.setOwner(this);
        return binding.getRoot();
    }

    @Override protected void initData() {
    }

    @Override public void onViewClick(View view) {
        if (view == binding.tvPositive) {

        } else if (view == binding.tvNeutral) {

        }
        dismissAllowingStateLoss();
    }

}
