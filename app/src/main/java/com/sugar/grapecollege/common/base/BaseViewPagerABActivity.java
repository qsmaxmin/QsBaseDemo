package com.sugar.grapecollege.common.base;

import com.qsmaxmin.qsbase.mvp.QsViewPagerABActivity;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.base.presenter.GrapeCollegePresenter;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:46
 * @Description
 */
public abstract class BaseViewPagerABActivity<P extends GrapeCollegePresenter> extends QsViewPagerABActivity<P> {

    /**
     * 状态布局切换时，执行的动画
     */
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }

    /**
     * 状态布局切换时，执行的动画
     */
    @Override public int viewStateOutAnimationId() {
        return super.viewStateOutAnimationId();
    }
}
