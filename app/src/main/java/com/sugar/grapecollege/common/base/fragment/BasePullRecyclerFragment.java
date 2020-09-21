package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.common.widget.ptr.PtrUIHandler;
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
    @Override public PtrUIHandler getPtrUIHandlerView() {
        return super.getPtrUIHandlerView();
    }

    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
