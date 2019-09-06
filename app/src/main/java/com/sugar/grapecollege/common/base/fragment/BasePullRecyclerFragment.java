package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.common.widget.ptr.PtrFrameLayout;
import com.qsmaxmin.qsbase.mvp.fragment.QsPullRecyclerFragment;
import com.qsmaxmin.qsbase.mvp.presenter.QsPresenter;
import com.sugar.grapecollege.R;


/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description
 */
public abstract class BasePullRecyclerFragment<P extends QsPresenter, D> extends QsPullRecyclerFragment<P, D> {

    /**
     * 重写可以自定义刷新头
     */
    @Override public PtrFrameLayout getPtrFrameLayout() {
        return super.getPtrFrameLayout();
    }

    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
