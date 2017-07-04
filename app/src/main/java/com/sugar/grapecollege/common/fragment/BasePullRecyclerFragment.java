package com.sugar.grapecollege.common.fragment;

import com.qsmaxmin.qsbase.common.widget.ptr.PtrUIHandler;
import com.qsmaxmin.qsbase.mvp.fragment.QsIPullRecyclerFragment;
import com.qsmaxmin.qsbase.mvp.fragment.QsPullRecyclerFragment;
import com.qsmaxmin.qsbase.mvp.presenter.QsPresenter;
import com.sugar.grapecollege.common.widget.refreshHeader.BeautyCircleRefreshHeader;


/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description
 */
public abstract class BasePullRecyclerFragment<P extends QsPresenter, D> extends QsPullRecyclerFragment<P, D> implements QsIPullRecyclerFragment<D> {

    private BeautyCircleRefreshHeader humanRefreshHeader;

    /**
     * 设置刷新头
     */
    @Override public PtrUIHandler getPtrUIHandlerView() {
        if (humanRefreshHeader == null) {
            humanRefreshHeader = new BeautyCircleRefreshHeader(getContext());
        }
        return humanRefreshHeader;
    }
}
