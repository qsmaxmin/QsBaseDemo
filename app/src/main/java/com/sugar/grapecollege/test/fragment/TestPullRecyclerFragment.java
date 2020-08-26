package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsmaxmin.annotation.bind.Bind;
import com.qsmaxmin.qsbase.mvp.adapter.QsRecycleAdapterItem;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.fragment.BasePullRecyclerFragment;
import com.sugar.grapecollege.test.model.TestModel;
import com.sugar.grapecollege.test.presenter.TestPullRecyclerPresenter;

import androidx.fragment.app.Fragment;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 18:14
 * @Description
 */
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

    class TestRecyclerAdapter extends QsRecycleAdapterItem<TestModel.TestModelInfo> {

        @Bind(R.id.tv_test) TextView tv_test;

        TestRecyclerAdapter(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent);
        }

        @Override protected int itemViewLayoutId() {
            return R.layout.item_test_list;
        }

        @Override protected void onBindItemData(TestModel.TestModelInfo testModelInfo, int i, int i1) {
            tv_test.setText(String.valueOf(testModelInfo.testName + "---->>>> 我是RecyclerView"));
        }
    }
}
