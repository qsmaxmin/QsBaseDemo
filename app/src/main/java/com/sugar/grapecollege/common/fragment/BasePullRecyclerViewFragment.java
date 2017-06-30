//package com.sugar.grapecollege.common.fragment;
//
//import com.sugar.grapecollege.common.widget.refreshHeader.BeautyCircleRefreshHeader;
//
//import j2w.team.common.widget.ptrLib.PtrUIHandler;
//import j2w.team.mvp.fragment.J2WPullRecyclerViewFragment;
//import j2w.team.mvp.presenter.J2WIPresenter;
//
///**
// * @CreateBy qsmaxmin
// * @Date 16/7/29
// * @Description
// */
//public abstract class BasePullRecyclerViewFragment<T extends J2WIPresenter> extends J2WPullRecyclerViewFragment<T> implements J2WIViewPullRecyclerViewFragment {
//
//    private BeautyCircleRefreshHeader humanRefreshHeader;
//
//    /**
//     * 设置刷新头
//     */
//    @Override public PtrUIHandler getPtrUIHandlerView() {
//        if (humanRefreshHeader == null) {
//            humanRefreshHeader = new BeautyCircleRefreshHeader(getContext());
//        }
//        return humanRefreshHeader;
//    }
//}
