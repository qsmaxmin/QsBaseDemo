package com.sugar.grapecollege.common.base;

import com.qsmaxmin.qsbase.mvp.QsABActivity;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:49
 * @Description
 */
public abstract class BaseABActivity<P extends GrapeCollegePresenter> extends QsABActivity<P> {
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
