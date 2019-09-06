package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.support.design.internal.BaselineLayout;
import android.support.v4.app.Fragment;

import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.qsmaxmin.qsbase.mvp.fragment.QsListFragment;
import com.sugar.grapecollege.common.base.fragment.BaseListFragment;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;
import com.sugar.grapecollege.test.adapter.TestListAdapter;
import com.sugar.grapecollege.test.model.TestModel;

import java.util.ArrayList;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:37
 * @Description
 */

public class TestListFragment extends BaseListFragment<GrapeCollegePresenter, TestModel.TestModelInfo> {

    @Override public QsListAdapterItem<TestModel.TestModelInfo> getListAdapterItem(int i) {
        return new TestListAdapter();
    }

    @Override public void initData(Bundle bundle) {
        setData(getTestListData().list);
    }

    private TestModel getTestListData() {
        TestModel model = new TestModel();
        model.code = 0;
        model.list = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            TestModel.TestModelInfo detail = new TestModel.TestModelInfo();
            detail.testName = getClass().getSimpleName() + "  " + i;
            model.list.add(detail);
        }
        return model;
    }

    public static Fragment getInstance() {
        return new TestListFragment();
    }

    @Override public boolean isDelayData() {
        return true;
    }
}
