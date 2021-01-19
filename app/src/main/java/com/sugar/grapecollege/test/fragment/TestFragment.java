package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.fragment.BaseFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:59
 * @Description
 */

public class TestFragment extends BaseFragment {

    public static Fragment getInstance() {
        return new TestFragment();
    }

    @Override public View onCreateContentView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return inflater.inflate(R.layout.fragment_user, parent, true);
    }

    @Override public void initData(Bundle bundle) {
        showContentView();
    }
}
