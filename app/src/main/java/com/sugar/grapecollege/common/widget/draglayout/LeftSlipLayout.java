package com.sugar.grapecollege.common.widget.draglayout;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.qsmaxmin.qsbase.common.log.L;

/**
 * @CreateBy qsmaxmin
 * @Date 17/2/14
 * @Description 左滑删除控件
 */
public class LeftSlipLayout extends FrameLayout {

    private GestureDetectorCompat gestureDetector;
    private ViewDragHelper        dragHelper;
    private DragListener          dragListener;
    private int                   range;
    private int                   sideWidth;
    private int                   mainLeft;
    private View                  sideView;
    private View                  mainView;

    private boolean mCanDrag   = true;
    private Status  lastStatus = Status.Close;


    public LeftSlipLayout(Context context) {
        this(context, null);
    }

    public LeftSlipLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftSlipLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        gestureDetector = new GestureDetectorCompat(context, new SimpleOnGestureListener() {
            @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return Math.abs(distanceY) <= Math.abs(distanceX);
            }
        });

        ViewDragHelper.Callback dragHelperCallback = new ViewDragHelper.Callback() {

            @Override public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left > 0) {
                    return 0;
                } else if (left < -range) {
                    return -range;
                } else {
                    return left;
                }
            }

            @Override public boolean tryCaptureView(View child, int pointerId) {
                return child == mainView;
            }

            @Override public int getViewHorizontalDragRange(View child) {
                return range;
            }

            @Override public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (xvel > 0) {
                    close();
                } else if (xvel < 0) {
                    open();
                } else if (mainLeft < -range * 0.5) {
                    open();
                } else {
                    close();
                }
            }

            @Override public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                mainLeft = left > 0 ? 0 : (left < -range ? -range : left);
                animateView();
                dispatchDragEvent(mainLeft);
            }
        };
        dragHelper = ViewDragHelper.create(this, 3f, dragHelperCallback);
    }


    private void dispatchDragEvent(int mainLeft) {
        lastStatus = getCurrentStatus();
        if (dragListener != null) {
            switch (lastStatus) {
                case Open:
                    dragListener.onOpen();
                    break;
                case Close:
                    dragListener.onClose();
                    break;
                case Drag:
                    dragListener.onDrag(mainLeft / (float) -range);
                    break;
            }
        }
    }

    private void animateView() {
        sideView.layout(getRight() + mainLeft, getTop(), getRight() + sideWidth + mainLeft, getBottom());
    }

    /*------------------------------ common override ---------------------------------------*/
    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        mainView = getChildAt(0);
        sideView = getChildAt(1);
        mainView.setClickable(true);
    }

    @Override protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        sideWidth = sideView.getMeasuredWidth();
        range = (int) (sideWidth * 1f);
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mainView.layout(left + mainLeft, top, right + mainLeft, bottom);
        sideView.layout(right + mainLeft, top, right + sideWidth + mainLeft, bottom);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean bDragHelper = dragHelper.shouldInterceptTouchEvent(ev);
        boolean bGestureDector = gestureDetector.onTouchEvent(ev);
        return mCanDrag && bDragHelper && bGestureDector;
    }

    @Override public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override public boolean onTouchEvent(MotionEvent e) {
        try {
            dragHelper.processTouchEvent(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /*--------------------------get and set-----------------------------*/
    public enum Status {
        Drag, Open, Close
    }

    private Status getCurrentStatus() {
        if (mainLeft == 0) {
            return Status.Close;
        } else if (mainLeft == -range) {
            return Status.Open;
        } else {
            return Status.Drag;
        }
    }

    public void resetOpenState() {
        mainLeft = 0;
        requestLayout();
    }

    public void setOpenState(boolean isOpen, boolean animation) {
        mainLeft = isOpen ? -range : 0;
        if (!animation) {
            requestLayout();
        } else {
            if (isOpen) {
                open(true, false);
            } else {
                close(true, false);
            }
        }
    }

    private void open() {
        open(true);
    }

    private void open(boolean animate) {
        open(animate, true);
    }

    private void open(boolean animate, boolean callBack) {
        if (animate) {
            if (dragHelper.smoothSlideViewTo(mainView, -range, getTop())) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mainLeft = -range;
            requestLayout();
            if (callBack) dispatchDragEvent(-range);
        }
    }

    public void close() {
        close(true);
    }

    public void close(boolean animate) {
        close(animate, true);
    }

    public void close(boolean animate, boolean callBack) {
        if (animate) {
            if (dragHelper.smoothSlideViewTo(mainView, getLeft(), getTop())) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mainLeft = 0;
            requestLayout();
            if (callBack) dispatchDragEvent(0);
        }
    }

    public boolean isOpen() {
        return lastStatus == Status.Open;
    }

    public boolean isClose() {
        return lastStatus == Status.Close;
    }

    public boolean isDrag() {
        return lastStatus == Status.Drag;
    }

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    public void setCanDrag(boolean canDrag) {
        this.mCanDrag = canDrag;
    }

    public boolean isCanDrag() {
        return mCanDrag;
    }

    public View getMainView() {
        return mainView;
    }

    public View getSideView() {
        return sideView;
    }

    public interface DragListener {
        void onOpen();

        void onClose();

        void onDrag(float percent);
    }
}
