package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;

import com.qsmaxmin.annotation.presenter.Presenter;
import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.fragment.BaseListFragment;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;
import com.sugar.grapecollege.test.adapter.TestListAdapter;
import com.sugar.grapecollege.test.model.TestModel;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:37
 * @Description
 */
@Presenter(GrapeCollegePresenter.class)
public class TestHeaderVPListFragment extends BaseListFragment<GrapeCollegePresenter, TestModel.TestModelInfo> {

    /**
     * 适配HeaderViewPager，必须使用InnerListView
     */
    @Override public int layoutId() {
        return R.layout.qs_fragment_inner_listview;
    }

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
        return new TestHeaderVPListFragment();
    }
}
