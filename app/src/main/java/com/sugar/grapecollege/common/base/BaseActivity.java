package com.sugar.grapecollege.common.base;

import com.qsmaxmin.qsbase.mvp.QsActivity;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:23
 * @Description
 */
public abstract class BaseActivity<P extends GrapeCollegePresenter> extends QsActivity<P> {

    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
