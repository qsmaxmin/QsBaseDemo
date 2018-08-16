package com.sugar.grapecollege.common.fragment;

import com.qsmaxmin.qsbase.common.widget.ptr.PtrUIHandler;
import com.qsmaxmin.qsbase.mvp.fragment.QsPullListFragment;
import com.qsmaxmin.qsbase.mvp.presenter.QsPresenter;
import com.sugar.grapecollege.common.widget.refreshHeader.BeautyCircleRefreshHeader;

/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description 抽象类，重写了下拉刷新动画
 */
public abstract class BasePullListFragment<T extends QsPresenter, D> extends QsPullListFragment<T, D>  {

    private BeautyCircleRefreshHeader refreshHeader;

    /**
     * 设置刷新头
     */
    @Override public PtrUIHandler getPtrUIHandlerView() {
        if (refreshHeader == null) {
            refreshHeader = new BeautyCircleRefreshHeader(getContext());
        }
        return refreshHeader;
    }
}
