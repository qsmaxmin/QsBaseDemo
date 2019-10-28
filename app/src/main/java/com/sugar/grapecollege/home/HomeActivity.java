package com.sugar.grapecollege.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.aspect.Permission;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.mvp.model.QsModelPager;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.BaseViewPagerABActivity;
import com.sugar.grapecollege.common.model.AppConfig;
import com.sugar.grapecollege.common.model.TestModel;
import com.sugar.grapecollege.home.fragment.MainFragment;
import com.sugar.grapecollege.home.fragment.UserFragment;
import com.sugar.grapecollege.home.model.HomeConstants;


public class HomeActivity extends BaseViewPagerABActivity {
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
     */
    private void testAppConfig() {
        L.i(initTag(), "testString = " + AppConfig.getInstance().toString());

        AppConfig.getInstance().testString = "hello world~";
        AppConfig.getInstance().testInt = 1;
        AppConfig.getInstance().testShort = 2;
        AppConfig.getInstance().testByte = 3;
        AppConfig.getInstance().testChar = 4;
        AppConfig.getInstance().testLong = 5;
        AppConfig.getInstance().testFloat = 6.6f;
        AppConfig.getInstance().testDouble = 7.7d;
        AppConfig.getInstance().testBoolean = true;

        AppConfig.getInstance().testInteger_ = 11;
        AppConfig.getInstance().testShort_ = 22;
        AppConfig.getInstance().testByte_ = 33;
        AppConfig.getInstance().testChar_ = 44;
        AppConfig.getInstance().testLong_ = 55L;
        AppConfig.getInstance().testFloat_ = 66.6f;
        AppConfig.getInstance().testDouble_ = 77.7d;
        AppConfig.getInstance().testBoolean_ = true;

        TestModel testModel = new TestModel();
        testModel.sss = "sssssssssss";
        AppConfig.getInstance().testModel = testModel;
        AppConfig.getInstance().commit();
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
        modelPager1.title = "title1";
        modelPager1.position = 0;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = new UserFragment();
        modelPager3.title = "title1";
        modelPager3.position = 1;
        Bundle bundle = new Bundle();
        bundle.putString(HomeConstants.BK_TEST, "hello...");
        modelPager3.fragment.setArguments(bundle);

        initViewPager(new QsModelPager[]{modelPager1, modelPager3}, 1);
    }

    @Override public int getTabItemLayout() {
        return R.layout.item_home_tab;
    }

    @Override public void initTab(View view, QsModelPager qsModelPager) {
        TextView tv_tab = view.findViewById(R.id.tv_tab);
        tv_tab.setText(qsModelPager.title);
    }

    @Override protected int getTabsSelectedTitleColor() {
        return getResources().getColor(R.color.colorAccent);
    }

    @Override protected int getTabsTitleColor() {
        return getResources().getColor(R.color.color_black);
    }
}
