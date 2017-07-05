package com.sugar.grapecollege.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.mvp.fragment.QsHeaderViewpagerFragment;
import com.qsmaxmin.qsbase.mvp.model.QsModelPager;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 14:45
 * @Description
 */

public class TestHeaderViewFragment extends QsHeaderViewpagerFragment {
    @Bind(R.id.tv_left) TextView tv_left;

    public static Fragment getInstance() {
        return new TestHeaderViewFragment();
    }

    @Override public int getHeaderLayout() {
        return R.layout.header_main_fragment;
    }

    @Override public QsModelPager[] getModelPagers() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = TestHeaderVPListFragment.getInstance();
        modelPager1.title = "title1";
        modelPager1.position = 0;

        QsModelPager modelPager2 = new QsModelPager();
        modelPager2.fragment = TestHeaderVPListFragment.getInstance();
        modelPager2.title = "title1";
        modelPager2.position = 0;
        return new QsModelPager[]{modelPager1, modelPager2};
    }

    @Override public void initData(Bundle savedInstanceState) {
        L.i(initTag(), "+++++++>>> " + tv_left);
    }

    @Override public boolean isOpenViewState() {
        return false;
    }
}
