//package com.sugar.grapecollege.common.fragment;
//
//import android.view.View;
//
//import com.sugar.grapecollege.common.widget.refreshHeader.BeautyCircleRefreshHeader;
//
//import j2w.team.common.widget.headerFooterRecycler.LoadingFooter;
//import j2w.team.common.widget.ptrLib.PtrUIHandler;
//import j2w.team.mvp.fragment.J2WPullListFragment;
//import j2w.team.mvp.presenter.J2WIPresenter;
//
///**
// * @CreateBy qsmaxmin
// * @Date 16/7/29
// * @Description 抽象类，重写了下拉刷新动画
// */
//public abstract class BasePullListFragment<T extends J2WIPresenter> extends J2WPullListFragment<T> implements J2WIViewPullListFragment {
//
//    private BeautyCircleRefreshHeader refreshHeader;
//
//    /**
//     * 设置刷新头
//     */
//    @Override public PtrUIHandler getPtrUIHandlerView() {
//        if (refreshHeader == null) {
//            refreshHeader = new BeautyCircleRefreshHeader(getContext());
//        }
//        return refreshHeader;
//    }
//
//    @Override protected void initListView(View view) {
//        super.initListView(view);
//        if (loadingFooter == null) {
//            return;
//        }
//        loadingFooter.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View view) {
//                if (loadingFooter != null && view == loadingFooter && loadingFooter.getState() == LoadingFooter.State.NetWorkError) {
//                    loadingMoreData();
//                }
//            }
//        });
//    }
//}
