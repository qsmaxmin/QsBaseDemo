package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.common.widget.ptr.PtrUIHandler;
import com.qsmaxmin.qsbase.mvvm.fragment.MvPullRecyclerFragment;
import com.sugar.grapecollege.R;

import org.jetbrains.annotations.NotNull;


/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description
 */
public abstract class BasePullRecyclerFragment<D> extends MvPullRecyclerFragment<D> {
    /**
     * 重写可以自定义刷新头
     */
    @NotNull @Override public PtrUIHandler getPtrUIHandlerView() {
        return super.getPtrUIHandlerView();
    }

    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
