package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.mvvm.fragment.MvListFragment;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:57
 * @Description
 */
public abstract class BaseListFragment<D> extends MvListFragment<D> {
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
