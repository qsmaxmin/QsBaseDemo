package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.common.widget.ptr.PtrUIHandler;
import com.qsmaxmin.qsbase.mvp.fragment.QsPullListFragment;
import com.qsmaxmin.qsbase.mvp.presenter.QsPresenter;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description 抽象类，重写了下拉刷新动画
 */
public abstract class BasePullListFragment<T extends QsPresenter, D> extends QsPullListFragment<T, D> {

    /**
     * 重写可以自定义刷新头
     */
    @Override public PtrUIHandler getPtrUIHandlerView() {
        return super.getPtrUIHandlerView();
    }

    /**
     * 列表加载更多的触发方式
     */
    @Override protected int onLoadTriggerCondition() {
        return LOAD_WHEN_SECOND_TO_LAST;
//        return LOAD_WHEN_SCROLL_TO_BOTTOM;
    }

    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
