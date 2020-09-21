package com.sugar.grapecollege.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.annotation.bind.Bind;
import com.qsmaxmin.annotation.permission.Permission;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.widget.viewpager.PagerSlidingTabStrip;
import com.qsmaxmin.qsbase.mvp.model.QsModelPager;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.BaseViewPagerActivity;
import com.sugar.grapecollege.common.model.AppConfig;
import com.sugar.grapecollege.home.fragment.MainFragment;
import com.sugar.grapecollege.home.fragment.UserFragment;
import com.sugar.grapecollege.home.model.HomeConstants;

import java.util.HashSet;


public class HomeActivity extends BaseViewPagerActivity {
    @Bind(R.id.tv_title) TextView tv_title;

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_title;
    }

    @SuppressLint("SetTextI18n")
    @Override public void initData(Bundle bundle) {
        tv_title.setText("custom bind view");
        requestPermission();
        testAppConfig();
    }

    /**
     * 测试数据序列化
     * 基于SharedPreferences存储，使用APT生成代码，方便，直观，高效
     */
    private void testAppConfig() {
        L.i(initTag(), "testString = " + AppConfig.getInstance().toString());
        if (TextUtils.isEmpty(AppConfig.getInstance().testString)) {
            AppConfig.getInstance().testString = "hello world~";
            AppConfig.getInstance().testInt = 1;
            AppConfig.getInstance().testLong = 5;
            AppConfig.getInstance().testFloat = 6.6f;
            AppConfig.getInstance().testBoolean = true;

            AppConfig.getInstance().testInteger_ = 11;
            AppConfig.getInstance().testLong_ = 55L;
            AppConfig.getInstance().testFloat_ = 66.6f;
            AppConfig.getInstance().testBoolean_ = true;

            AppConfig.getInstance().testSet = new HashSet<>();
            AppConfig.getInstance().testSet.add("1111111");
            AppConfig.getInstance().testSet.add("2222222");
            AppConfig.getInstance().commit();
        }
    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }

    /**
     * 因为要申请完权限通过后再初始化ViewPager
     * 所以返回null
     */
    @Override public QsModelPager[] getModelPagers() {
        return null;
    }

    @Permission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA})
    public void requestPermission() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = new MainFragment();
        modelPager1.title = "首页";
        modelPager1.position = 0;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = new UserFragment();
        modelPager3.title = "我的";
        modelPager3.position = 1;
        Bundle bundle = new Bundle();
        bundle.putString(HomeConstants.BK_TEST, "hello...");
        modelPager3.fragment.setArguments(bundle);

        initViewPager(new QsModelPager[]{modelPager1, modelPager3}, 1);
    }

    @Override public int getTabItemLayout() {
        return R.layout.item_home_tab;
    }

    @Override public void initTabItem(View tabItem, QsModelPager modelPager) {
        TextView tv_tab = tabItem.findViewById(R.id.tv_tab);
        tv_tab.setText(modelPager.title);

    }

    @Override public void initTab(PagerSlidingTabStrip tabStrip) {
        super.initTab(tabStrip);
        tabStrip.setTextColor(Color.GRAY);
        tabStrip.setSelectedTextColor(Color.RED);

        tabStrip.setIndicatorColor(Color.RED);
        tabStrip.setIndicatorWidth(100);
        tabStrip.setIndicatorHeight(10);

        tabStrip.setShouldExpand(true);
        //......
    }
}
