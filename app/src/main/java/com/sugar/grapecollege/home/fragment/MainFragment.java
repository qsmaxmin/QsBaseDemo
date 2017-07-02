package com.sugar.grapecollege.home.fragment;

import android.os.Bundle;

import com.qsmaxmin.qsbase.common.aspect.ThreadPoint;
import com.qsmaxmin.qsbase.common.aspect.ThreadType;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
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
public class MainFragment extends QsFragment<MainPresenter> {

    @Override public int layoutId() {
        return R.layout.fragment_main;
    }

    @Override public void initData(Bundle bundle) {
        getPresenter().requestBannerData();
    }

    @ThreadPoint(ThreadType.MAIN) public void updateHeader(ModelHomeHeader header) {
        L.i(initTag(), "updateHeader 当前线程:" + Thread.currentThread().getName());
        showContentView();
        if (header != null && header.responseData != null && !header.responseData.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (ModelHomeHeader.ResponseDataModel model : header.responseData) {
                arrayList.add(model.picUrl);
            }
        }
    }

    @OnClick(R.id.bt_search) public void onItemViewClick() {
        intent2Activity(SearcherActivity.class);
    }


    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onResume() {
        super.onResume();
    }
}
