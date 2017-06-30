//package com.sugar.grapecollege.common.widget.refreshHeader;
//
//import android.content.Context;
//import android.support.v4.view.ViewCompat;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.sugar.grapecollege.common.utils.CommonUtils;
//
//import com.qsmaxmin.qsbase.common.log.L;
//import j2w.team.common.widget.ptrLib.PtrFrameLayout;
//import j2w.team.common.widget.ptrLib.PtrUIHandler;
//import j2w.team.common.widget.ptrLib.indicator.PtrIndicator;
//
//
///**
// * @CreateBy qsmaxmin
// * @Date 16/8/19  上午11:18
// * @Description
// */
//public class BeautyCircleRefreshHeader extends RelativeLayout implements PtrUIHandler {
//
//    private static final int HEADER_HEIGHT = CommonUtils.dp2px(50);
//    private ImageView            ivRefresh;
//    private BeautyCircleDrawable circleLogoDrawable;
//
//    public BeautyCircleRefreshHeader(Context context) {
//        this(context, null);
//    }
//
//    public BeautyCircleRefreshHeader(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public BeautyCircleRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView();
//    }
//
//    private void initView() {
//        setGravity(Gravity.CENTER);
//        addView(getHeaderView());
//    }
//
//    private View getHeaderView() {
//        if (ivRefresh == null) {
//            ivRefresh = new ImageView(getContext());
//            ivRefresh.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            circleLogoDrawable = new BeautyCircleDrawable();
//            ivRefresh.setBackgroundDrawable(circleLogoDrawable);
//            LayoutParams imageParams = new LayoutParams(CommonUtils.dp2px(35), CommonUtils.dp2px(35));
//            imageParams.addRule(CENTER_IN_PARENT, TRUE);
//            ivRefresh.setLayoutParams(imageParams);
//        }
//        RelativeLayout headerLayout = new RelativeLayout(getContext());
//        LayoutParams layoutParams = new LayoutParams(HEADER_HEIGHT, HEADER_HEIGHT);
//        headerLayout.setLayoutParams(layoutParams);
//        headerLayout.addView(ivRefresh);
//        return headerLayout;
//    }
//
//    public void setPtrFrameLayout(PtrFrameLayout layout) {
//        layout.setOffsetToKeepHeaderWhileLoading((int) CommonUtils.dp2px(getResources(), 80));
//    }
//
//    /**
//     * 准备刷新
//     */
//    @Override public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
//        L.i("++++++++++   onUIRefreshPrepare");
//        if (circleLogoDrawable != null) {
//            circleLogoDrawable.onPrepare();
//        }
//    }
//
//    /**
//     * 开始刷新
//     */
//    @Override public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
//        L.i("++++++++++   onUIRefreshBegin");
//        if (circleLogoDrawable != null) {
//            circleLogoDrawable.onBegin();
//        }
//    }
//
//    /**
//     * 刷新完成
//     */
//    @Override public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {
//        L.i("++++++++++   onUIRefreshComplete" + System.currentTimeMillis());
//        if (circleLogoDrawable != null) {
//            circleLogoDrawable.onRefreshComplete();
//        }
//    }
//
//    /**
//     * 刷新重置
//     */
//    @Override public void onUIReset(PtrFrameLayout ptrFrameLayout) {
//        L.i("++++++++++   onUIReset");
//        if (circleLogoDrawable != null) {
//            circleLogoDrawable.onReset();
//        }
//    }
//
//    /**
//     * 当下拉高度高于设定临界点时改变刷新头状态
//     * 临界点比例：{@link PtrIndicator#mRatioOfHeaderHeightToRefresh}
//     */
//    @Override public void onUIPositionChange(PtrFrameLayout ptrFrameLayout, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
//        L.i("++++   onUIPositionChange" + System.currentTimeMillis());
//        float percent = Math.min(ptrIndicator.getRatioOfHeaderToHeightRefresh(), ptrIndicator.getCurrentPercent());
//        if (checkCanAnimation()) {
//            circleLogoDrawable.setPercent(percent);
//            circleLogoDrawable.setIsReachCriticalPoint(percent == ptrIndicator.getRatioOfHeaderToHeightRefresh());
//            ViewCompat.setTranslationY(ivRefresh, (1f - percent) * HEADER_HEIGHT / 2);
//        }
//    }
//
//    private boolean checkCanAnimation() {
//        return circleLogoDrawable != null && ivRefresh != null;
//    }
//}
