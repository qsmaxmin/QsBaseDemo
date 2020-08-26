package com.sugar.grapecollege.test.adapter;

import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.annotation.bind.Bind;
import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.test.model.TestModel;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:40
 * @Description
 */

public class TestListAdapter extends QsListAdapterItem<TestModel.TestModelInfo> {
    @Bind(R.id.tv_test) TextView tv_test;

    @Override public int getItemLayout() {
        return R.layout.item_test_list;
    }

    @Override public void bindData(TestModel.TestModelInfo testModelInfo, int i, int i1) {
        tv_test.setText(String.valueOf(testModelInfo.testName + " -->>>> 我是ListView"));
    }
}
