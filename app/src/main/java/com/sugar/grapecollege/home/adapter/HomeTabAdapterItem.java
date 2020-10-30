package com.sugar.grapecollege.home.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.qsmaxmin.annotation.bind.Bind;
import com.qsmaxmin.qsbase.mvp.adapter.QsTabAdapterItem;
import com.qsmaxmin.qsbase.mvp.model.QsModelPager;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/10/30 16:10
 * @Description
 */
public class HomeTabAdapterItem extends QsTabAdapterItem {
    @Bind(R.id.tv_tab) TextView tv_tab;

    public HomeTabAdapterItem(int position) {
        super(position);
    }

    @Override public int tabItemLayoutId() {
        return R.layout.item_home_tab;
    }

    @Override public void bindData(QsModelPager pagers, int position) {
        tv_tab.setText(pagers.title);
        onPageSelectChanged(position == 0);
    }

    @Override public void onPageSelectChanged(boolean selected) {
        tv_tab.setTextColor(selected ? Color.GREEN : Color.GRAY);
    }
}
