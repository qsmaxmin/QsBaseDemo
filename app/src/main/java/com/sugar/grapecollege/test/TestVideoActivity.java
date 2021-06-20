package com.sugar.grapecollege.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsmaxmin.qsbase.mvvm.MvActivity;
import com.sugar.grapecollege.databinding.ActivityTestVideoBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @CreateBy qsmaxmin
 * @Date 6/19/21  6:18 PM
 * @Description
 */
public class TestVideoActivity extends MvActivity {
    private ActivityTestVideoBinding contentBinding;

    @Override public View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
        contentBinding = ActivityTestVideoBinding.inflate(inflater, parent, false);
        contentBinding.setOwner(this);
        return contentBinding.getRoot();
    }

    @Override public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }
}
