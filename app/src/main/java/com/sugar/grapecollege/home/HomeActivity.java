package com.sugar.grapecollege.home;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.aspect.Permission;
import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.mvp.QsViewPagerABActivity;
import com.qsmaxmin.qsbase.mvp.model.QsModelPager;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.home.fragment.MainFragment;
import com.sugar.grapecollege.home.fragment.UserFragment;


public class HomeActivity extends QsViewPagerABActivity {

    @Bind(R.id.tv_title) TextView tv_title;

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_title;
    }

    @Override public void initData(Bundle bundle) {
        tv_title.setText("custom bind view");
        requestPermission();
    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }

    /**
     * 因为要申请完权限通过后再初始化ViewPager
     * 所以返回null
     */
    @Override public QsModelPager[] getModelPagers() {
//        QsModelPager modelPager1 = new QsModelPager();
//        modelPager1.fragment = MainFragment.getInstance();
//        modelPager1.title = "title1";
//        modelPager1.position = 0;
//
//        QsModelPager modelPager3 = new QsModelPager();
//        modelPager3.fragment = UserFragment.getInstance();
//        modelPager3.title = "title1";
//        modelPager3.position = 1;
//        return new QsModelPager[]{modelPager1, modelPager3};
        return null;
    }

    @Permission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}) public void requestPermission() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = MainFragment.getInstance();
        modelPager1.title = "title1";
        modelPager1.position = 0;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = UserFragment.getInstance();
        modelPager3.title = "title1";
        modelPager3.position = 1;

        initViewPager(new QsModelPager[]{modelPager1, modelPager3}, 3);
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
