package com.sugar.grapecollege.common.utils.zoom;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.widget.photoview.PhotoView;
import com.sugar.grapecollege.common.widget.photoview.PhotoViewAttacher;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/27 10:35
 * @Description
 */

class ZoomClickListener implements View.OnClickListener {

    private Activity           mActivity;
    private View               mTarget;
    private PhotoView          mZoomableView;
    private View               shadowView;
    private ZoomHelper.Builder mBuilder;
    private float              screenWidth;
    private float              screenHeight;
    private float              ratio;
    private int                viewWidth;
    private int                viewHeight;
    private int                viewStartX;
    private int                viewStartY;

    private Interpolator interpolator;

    private ZoomListener mZoomListener;

    private Runnable mEndRunnable = new Runnable() {
        @Override public void run() {
            removeFromDecorView(mZoomableView);
            removeFromDecorView(shadowView);
            mTarget.setVisibility(View.VISIBLE);
            if (mZoomListener != null) mZoomListener.onViewEndedZooming(mTarget);
            if (mBuilder.isHideStatusBar()) showSystemUI();
        }
    };

    private void initView() {
        if (mTarget != null) {
            initZoomView();
            initShadowView();
            addToDecorView(shadowView);
            addToDecorView(mZoomableView);

            mTarget.getParent().requestDisallowInterceptTouchEvent(true);
            mTarget.setVisibility(View.INVISIBLE);

            if (mBuilder.isHideStatusBar()) hideSystemUI();
            if (mZoomListener != null) mZoomListener.onViewStartedZooming(mTarget);
        }
    }

    private void initShadowView() {
        if (shadowView == null) {
            shadowView = new View(mActivity);
            shadowView.setBackgroundColor(0x88000000);
            shadowView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            shadowView.setId(R.id.zoom_shadow);
        }
        shadowView.setAlpha(0f);
    }

    private void initZoomView() {
        mZoomableView = new PhotoView(mActivity);
        mZoomableView.setImageBitmap(ZoomUtils.getBitmapFromView(mTarget));
        mZoomableView.setId(R.id.zoom_view);
        mZoomableView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override public void onViewTap(View view, float x, float y) {
                endZoomingView();
            }
        });
        mZoomableView.setOnScaleChangeListener(new PhotoViewAttacher.OnScaleChangeListener() {
            @Override public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                float scale = mZoomableView.getScale();
                if (scale < mZoomableView.getMinimumScale() * .7f) {
                    mZoomableView.setEnabled(false);
                    endZoomingView();
                }
            }
        });

        mZoomableView.setOnKeyDownListener(new KeyEvent.Callback() {
            @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mZoomableView != null) {
                    endZoomingView();
                    return true;
                }
                return false;
            }

            @Override public boolean onKeyLongPress(int keyCode, KeyEvent event) {
                return false;
            }

            @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
                return false;
            }

            @Override public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
                return false;
            }
        });

        Point viewAbsolutePoint = ZoomUtils.getViewAbsolutePoint(mTarget);
        int width = mTarget.getWidth();
        int height = mTarget.getHeight();

        if (width > 0 && height > 0) {
            ratio = Math.min(screenWidth / width, screenHeight / height);
            viewWidth = (int) (screenWidth / ratio);
            viewHeight = (int) (screenHeight / ratio);
            mZoomableView.setLayoutParams(new ViewGroup.LayoutParams(viewWidth, viewHeight));
            mZoomableView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            viewStartX = viewAbsolutePoint.x - ((viewWidth - width) / 2);
            viewStartY = viewAbsolutePoint.y - ((viewHeight - height) / 2);
            mZoomableView.setX(viewStartX);
            mZoomableView.setY(viewStartY);
        }
    }

    ZoomClickListener(ZoomHelper.Builder builder) {
        this.mBuilder = builder;
        this.mActivity = builder.getActivity();
        this.mTarget = builder.getTargetView();
        this.mZoomListener = builder.getZoomListener();
        this.interpolator = builder.getInterpolator();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }


    @Override public void onClick(View v) {
        if (mZoomListener != null) mZoomListener.onClick(v);
        initView();
        autoZoomingView();
    }

    private void endZoomingView() {
        if (mZoomableView != null) {
            if (mBuilder.isAnimate()) {
                float scale = mZoomableView.getScale();
                scale = (scale > 1 || scale < 0) ? 1 : scale;
                ViewPropertyAnimator animator = mZoomableView.animate().x(viewStartX).y(viewStartY).scaleX(1 / scale).scaleY(1 / scale).setInterpolator(interpolator);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    animator.withEndAction(mEndRunnable);
                }
                animator.start();
            } else {
                mEndRunnable.run();
            }
        }
        if (shadowView != null) {
            shadowView.animate().alpha(0f).start();
        }
    }

    private void autoZoomingView() {
        if (mZoomListener != null) mZoomListener.onViewStartedZooming(mTarget);
        if (mBuilder.isHideStatusBar()) hideSystemUI();
        if (mZoomableView != null) {
            float x = (screenWidth - viewWidth) / 2;
            float y = (screenHeight - viewHeight) / 2;
            if (mBuilder.isAnimate()) {
                mZoomableView.animate().x(x).y(y).scaleX(ratio).scaleY(ratio).setInterpolator(interpolator).start();
            } else {
                mZoomableView.setX(x);
                mZoomableView.setY(y);
                mZoomableView.setScaleX(ratio);
                mZoomableView.setScaleY(ratio);
            }
        }
        if (shadowView != null) {
            shadowView.animate().alpha(1f).start();
        }
    }

    private void addToDecorView(View v) {
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        View view = decorView.findViewById(v.getId());
        if (view == null) decorView.addView(v);
    }

    private void removeFromDecorView(View v) {
        ((ViewGroup) mActivity.getWindow().getDecorView()).removeView(v);
    }

    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    private void showSystemUI() {
        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }
}
