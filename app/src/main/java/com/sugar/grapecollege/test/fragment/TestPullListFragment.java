package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.sugar.grapecollege.common.fragment.BasePullListFragment;
import com.sugar.grapecollege.test.adapter.TestListAdapter;
import com.sugar.grapecollege.test.model.TestModel;
import com.sugar.grapecollege.test.presenter.TestPullListPresenter;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:50
 * @Description
 */
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
        L.i("----------->>>", "position:" + position);
    }

    @Override public boolean isDelayData() {
        return true;
    }
}
