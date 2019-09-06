package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.mvp.fragment.QsListFragment;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:57
 * @Description
 */
public abstract class BaseListFragment<P extends GrapeCollegePresenter, D> extends QsListFragment<P, D> {
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
