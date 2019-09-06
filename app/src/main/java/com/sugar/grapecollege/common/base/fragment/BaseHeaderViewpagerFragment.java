package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.mvp.fragment.QsHeaderViewpagerFragment;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:56
 * @Description
 */
public abstract class BaseHeaderViewpagerFragment<P extends GrapeCollegePresenter> extends QsHeaderViewpagerFragment<P> {
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
