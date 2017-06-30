package com.sugar.grapecollege.common.utils.zoom;

import android.app.Activity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.qsmaxmin.qsbase.common.log.L;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/27 10:38
 * @Description view查看帮助类，可以对任何view在当前页面进行放大查看，并支持手势缩放等操作
 */

public class ZoomHelper {

    private static ZoomHelper helper = new ZoomHelper();

    private ZoomHelper() {
    }

    public static ZoomHelper getInstance() {
        return helper;
    }

    public ZoomHelper.Builder createBuilder(Activity activity) {
        return new Builder(activity);
    }

    public class Builder {
        private Interpolator mZoomInterpolator = new DecelerateInterpolator();
        private boolean      mAnimate          = true;
        private boolean      mHideStatusBar    = false;

        private Activity     mActivity;
        private View         mTargetView;
        private ZoomListener mZoomListener;

        private Builder(Activity activity) {
            this.mActivity = activity;
        }

        public ZoomHelper.Builder target(View target) {
            this.mTargetView = target;
            return this;
        }

        public ZoomHelper.Builder animate(boolean animate) {
            this.mAnimate = animate;
            return this;
        }

        public ZoomHelper.Builder hideStatusBar(boolean showStatusBar) {
            this.mHideStatusBar = showStatusBar;
            return this;
        }

        public ZoomHelper.Builder setInterpolator(Interpolator interpolator) {
            this.mZoomInterpolator = interpolator;
            return this;
        }

        public ZoomHelper.Builder setZoomListener(ZoomListener listener) {
            this.mZoomListener = listener;
            return this;
        }

        public Activity getActivity() {
            return mActivity;
        }

        public View getTargetView() {
            return mTargetView;
        }

        public boolean isAnimate() {
            return mAnimate;
        }

        public boolean isHideStatusBar() {
            return mHideStatusBar;
        }

        public ZoomListener getZoomListener() {
            return mZoomListener;
        }

        public Interpolator getInterpolator() {
            return mZoomInterpolator;
        }

        public void register() {
            if (mActivity == null) {
                L.e("ZoomHelper", "Activity must not be null");
                return;
            }
            if (mTargetView == null) {
                L.e("ZoomHelper", "Target view must not be null");
                return;
            }
            mTargetView.setClickable(true);
            mTargetView.setOnClickListener(new ZoomClickListener(this));
        }
    }
}
