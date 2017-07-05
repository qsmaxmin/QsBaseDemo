package com.sugar.grapecollege.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.qsmaxmin.qsbase.common.aspect.ThreadPoint;
import com.qsmaxmin.qsbase.common.aspect.ThreadType;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.viewbind.annotation.OnClick;
import com.qsmaxmin.qsbase.mvp.adapter.QsListAdapterItem;
import com.qsmaxmin.qsbase.mvp.presenter.Presenter;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.fragment.BasePullListFragment;
import com.sugar.grapecollege.home.adapter.MainListAdapterItem;
import com.sugar.grapecollege.home.model.ModelHomeHeader;
import com.sugar.grapecollege.home.presenter.MainPresenter;
import com.sugar.grapecollege.product.model.ModelProductInfo;
import com.sugar.grapecollege.test.TestABActivity;
import com.sugar.grapecollege.test.TestActivity;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/27 15:58
 * @Description
 */
@Presenter(MainPresenter.class)
public class MainFragment extends BasePullListFragment<MainPresenter, ModelProductInfo.ProductInfo> {

    public static Fragment getInstance() {
        return new MainFragment();
    }

    @Override public int getHeaderLayout() {
        return R.layout.header_main_fragment;
    }

    @Override public QsListAdapterItem<ModelProductInfo.ProductInfo> getListAdapterItem(int i) {
        return new MainListAdapterItem();
    }

    @Override public void onRefresh() {
        getPresenter().requestListData(false, false);
    }

    @Override public void onLoad() {
        getPresenter().requestListData(true, false);
    }

    @Override public void initData(Bundle bundle) {
        getPresenter().requestBannerData();
        getPresenter().requestListData(false, true);
    }

    @ThreadPoint(ThreadType.MAIN) public void updateHeader(ModelHomeHeader header) {
        L.i(initTag(), "updateHeader 当前线程:" + Thread.currentThread().getName());
        showContentView();
    }

    @OnClick({R.id.tv_left, R.id.tv_right}) private void onItemViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                intent2Activity(TestActivity.class);
                break;
            case R.id.tv_right:
                intent2Activity(TestABActivity.class);
                break;
        }
    }

}
