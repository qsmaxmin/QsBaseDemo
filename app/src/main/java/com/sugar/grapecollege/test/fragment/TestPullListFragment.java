package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.qsmaxmin.annotation.presenter.Presenter;
import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.sugar.grapecollege.common.base.fragment.BasePullListFragment;
import com.sugar.grapecollege.test.adapter.TestListAdapter;
import com.sugar.grapecollege.test.model.TestModel;
import com.sugar.grapecollege.test.presenter.TestPullListPresenter;

import androidx.fragment.app.Fragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:50
 * @Description
 */
@Presenter(TestPullListPresenter.class)
public class TestPullListFragment extends BasePullListFragment<TestPullListPresenter, TestModel.TestModelInfo> {

    @Override public void onRefresh() {
        getPresenter().requestListData(false);
    }

    @Override public void onLoad() {
        getPresenter().requestListData(true);
    }

    @Override public QsListAdapterItem<TestModel.TestModelInfo> getListAdapterItem(int i) {
        return new TestListAdapter();
    }

    @Override public void initData(Bundle bundle) {
        getPresenter().requestListData(false);
    }

    public static Fragment getInstance() {
        return new TestPullListFragment();
    }

    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
    }

    @Override public boolean isDelayData() {
        return true;
    }
}
