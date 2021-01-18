package com.sugar.grapecollege.home.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsmaxmin.qsbase.mvvm.adapter.MvTabAdapterItem;
import com.qsmaxmin.qsbase.mvvm.model.MvModelPager;
import com.sugar.grapecollege.R;
/**
 * @CreateBy qsmaxmin
 * @Date 2020/10/30 16:10
 * @Description
 */
public class HomeTabAdapterItem extends MvTabAdapterItem {
    private TextView tv_tab;

    public HomeTabAdapterItem(int position) {
        super(position);
    }

    @Override public View onCreateTabItemView(LayoutInflater inflater, ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.item_home_tab, parent, false);
        tv_tab = inflate.findViewById(R.id.tv_tab);
        return inflater.inflate(R.layout.item_home_tab, parent, false);
    }

    @Override public void bindData(MvModelPager pagers, int position) {
        tv_tab.setText(pagers.title);
        onPageSelectChanged(position == 0);
    }


    @Override public void onPageSelectChanged(boolean selected) {
        tv_tab.setTextColor(selected ? Color.GREEN : Color.GRAY);
    }
}
