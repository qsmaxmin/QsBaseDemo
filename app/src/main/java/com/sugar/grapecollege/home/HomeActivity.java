package com.sugar.grapecollege.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.mvp.QsViewPagerABActivity;
import com.qsmaxmin.qsbase.mvp.model.QsModelPager;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.home.fragment.MainFragment;
import com.sugar.grapecollege.home.fragment.UserFragment;

import butterknife.Bind;

public class HomeActivity extends QsViewPagerABActivity {

    @Bind(R.id.tv_title) TextView tv_title;

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_title;
    }

    @Override public void initData(Bundle bundle) {

    }

    @Override public QsModelPager[] getModelPagers() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = UserFragment.getInstance();
        modelPager1.title = "title1";
        modelPager1.position = 0;

        QsModelPager modelPager2 = new QsModelPager();
        modelPager2.fragment = MainFragment.getInstance();
        modelPager2.title = "title1";
        modelPager2.position = 0;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = UserFragment.getInstance();
        modelPager3.title = "title1";
        modelPager3.position = 0;
        return new QsModelPager[]{modelPager1, modelPager2, modelPager3};
    }

    @Override public int getTabItemLayout() {
        return R.layout.item_home_tab;
    }

    @Override public void initTab(View view, QsModelPager qsModelPager) {
        TextView tv_tab = (TextView) view.findViewById(R.id.tv_tab);
        tv_tab.setText(qsModelPager.title);
    }

    @Override protected int getTabsSelectedTitleColor() {
        return getResources().getColor(R.color.colorAccent);
    }

    @Override protected int getTabsTitleColor() {
        return getResources().getColor(R.color.color_black);
    }
}
