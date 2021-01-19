package com.sugar.grapecollege.common.base.fragment;

import com.qsmaxmin.qsbase.mvvm.fragment.MvViewPagerFragment;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:59
 * @Description
 */
public abstract class BaseViewPagerFragment extends MvViewPagerFragment {
    @Override public int viewStateInAnimationId() {
        return R.anim.view_state_in;
    }
}
