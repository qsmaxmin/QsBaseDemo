package com.sugar.grapecollege.home.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qsmaxmin.qsbase.common.aspect.Permission;
import com.qsmaxmin.qsbase.common.aspect.ThreadPoint;
import com.qsmaxmin.qsbase.common.aspect.ThreadType;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.mvp.fragment.QsViewPagerFragment;
import com.qsmaxmin.qsbase.mvp.model.QsModelPager;
import com.qsmaxmin.qsbase.mvp.presenter.Presenter;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.home.model.ModelHomeHeader;
import com.sugar.grapecollege.home.presenter.MainPresenter;
import com.sugar.grapecollege.searcher.SearcherActivity;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/27 15:58
 * @Description
 */
@Presenter(MainPresenter.class)
public class MainFragment extends QsViewPagerFragment<MainPresenter> {

    @Override public QsModelPager[] getModelPagers() {
        return createModelPagers();
    }

    @Override public void initData(Bundle bundle) {
        getPresenter().requestBannerData();
    }

    @ThreadPoint(ThreadType.MAIN) public void updateHeader(ModelHomeHeader header) {
        L.i(initTag(), "updateHeader 当前线程:" + Thread.currentThread().getName());
        showContentView();
    }

    private QsModelPager[] createModelPagers() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = UserFragment.getInstance();
        modelPager1.title = "title1";
        modelPager1.position = 0;

        QsModelPager modelPager2 = new QsModelPager();
        modelPager2.fragment = UserFragment.getInstance();
        modelPager2.title = "title1";
        modelPager2.position = 0;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = UserFragment.getInstance();
        modelPager3.title = "title1";
        modelPager3.position = 0;

        return new QsModelPager[]{modelPager1, modelPager2, modelPager3};
    }


    @Permission(values = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}) private void goSearch() {
        intent2Activity(SearcherActivity.class);
    }

    public static Fragment getInstance() {
        return new MainFragment();
    }
}
