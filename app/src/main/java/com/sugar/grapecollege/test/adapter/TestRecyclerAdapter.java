package com.sugar.grapecollege.test.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.mvp.adapter.QsRecycleAdapterItem;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.test.model.TestModel;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:40
 * @Description
 */

public class TestRecyclerAdapter extends QsRecycleAdapterItem<TestModel.TestModelInfo> {

    @Bind(R.id.tv_test) TextView tv_test;

    public TestRecyclerAdapter(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent);
    }

    @Override protected int itemViewLayoutId() {
        return R.layout.item_test_list;
    }

    @Override protected void onBindItemData(TestModel.TestModelInfo testModelInfo, int i, int i1) {
        tv_test.setText(String.valueOf(testModelInfo.testName + "---->>>> 我是RecyclerView"));
    }
}
