package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.mvp.fragment.QsViewPagerFragment;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:59
 * @Description
 */
public abstract class BaseViewPagerFragment<P extends GrapeCollegePresenter> extends QsViewPagerFragment<P> {
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
