package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.mvp.adapter.QsRecycleAdapterItem;
import com.qsmaxmin.qsbase.mvp.presenter.Presenter;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.fragment.BasePullRecyclerFragment;
import com.sugar.grapecollege.test.adapter.TestRecyclerAdapter;
import com.sugar.grapecollege.test.model.TestModel;
import com.sugar.grapecollege.test.presenter.TestPullRecyclerPresenter;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 18:14
 * @Description
 */
@Presenter(TestPullRecyclerPresenter.class)
public class TestPullRecyclerFragment extends BasePullRecyclerFragment<TestPullRecyclerPresenter, TestModel.TestModelInfo> {

    @Bind(R.id.tv_left) TextView tv_left;

    @Override public int getHeaderLayout() {
        return R.layout.header_main_fragment;
    }

    public static Fragment getInstance() {
        return new TestPullRecyclerFragment();
    }

    @Override public QsRecycleAdapterItem<TestModel.TestModelInfo> getRecycleAdapterItem(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new TestRecyclerAdapter(layoutInflater, viewGroup);
    }

    @Override public void onRefresh() {
        getPresenter().requestListData(false);
    }

    @Override public void onLoad() {
        getPresenter().requestListData(true);
    }

    @Override public void initData(Bundle bundle) {
        tv_left.setText("呵呵思密达");
        getPresenter().requestListData(false);
    }
}
