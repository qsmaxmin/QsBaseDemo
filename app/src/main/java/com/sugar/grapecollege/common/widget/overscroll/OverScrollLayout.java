package com.sugar.grapecollege.common.widget.overscroll;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/24 14:20
 * @Description 过滑动容器，当childView滑动到不能滑动时，可以继续滑动一段距离并且在松手后回弹到开始位置
 * 支持：
 * 所有的Layout(默认垂直方向过滑动，可通过设置{@link #setLeftOverScrollEnable(boolean)}等进行设置)
 * {@link WebView}
 * {@link ScrollView}
 * {@link HorizontalScrollView}
 * {@link AbsListView}
 * {@link RecyclerView}
 */
public class OverScrollLayout extends RelativeLayout {

    private float   fraction               = 0.5f;
    private boolean topOverScrollEnable    = true;
    private boolean bottomOverScrollEnable = true;
    private boolean leftOverScrollEnable   = true;
    private boolean rightOverScrollEnable  = true;

    private float                downY;
    private float                oldY;
    private int                  dealtY;
    private float                downX;
    private float                oldX;
    private int                  dealtX;
    private boolean              isVerticalMove;
    private boolean              isHorizontallyMove;
    private boolean              isOverScrollTop;
    private boolean              isOverScrollBottom;
    private boolean              isOverScrollLeft;
    private boolean              isOverScrollRight;
    private boolean              checkScrollDirectionFinish;
    private boolean              canOverScrollHorizontally;
    private boolean              canOverScrollVertical;
    private float                baseOverScrollLength;
    private boolean              finishOverScroll;
    private boolean              abortScroller;
    private boolean              shouldSetScrollerStart;
    private boolean              disallowIntercept;
    private View                 child;
    private ViewConfiguration    configuration;
    private GestureDetector      detector;
    private Scroller             mScroller;
    private OverScroller         overScroller;
    private FlingRunnable        flingRunnable;
    private OverScrollRunnable   overScrollRunnable;
    private OnOverScrollListener onOverScrollListener;

    public OverScrollLayout(Context context) {
        super(context);
        init();
    }

    public OverScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        configuration = ViewConfiguration.get(getContext());
        mScroller = new Scroller(getContext(), new DecelerateInterpolator(2f));
        flingRunnable = new FlingRunnable();
        overScrollRunnable = new OverScrollRunnable();
        overScroller = new OverScroller(getContext());
        detector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (isOverScrollTop || isOverScrollBottom || isOverScrollLeft || isOverScrollRight) {
                    return false;
                }
                flingRunnable.startFling(velocityX, velocityY);
                return false;
            }
        });
    }

    @Override protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new IllegalStateException("OverScrollLayout only can host 1 element");
        } else if (childCount == 1) {
            child = getChildAt(0);
            child.setOverScrollMode(OVER_SCROLL_NEVER);
        }
        super.onFinishInflate();
    }

    public void setDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.disallowIntercept = disallowIntercept;
    }

    public boolean isTopOverScrollEnable() {
        return topOverScrollEnable;
    }

    /**
     * @param topOverScrollEnable true can over scroll top false otherwise
     */
    public void setTopOverScrollEnable(boolean topOverScrollEnable) {
        this.topOverScrollEnable = topOverScrollEnable;
    }

    public boolean isBottomOverScrollEnable() {
        return bottomOverScrollEnable;
    }

    /**
     * @param bottomOverScrollEnable true can over scroll bottom false otherwise
     */
    public void setBottomOverScrollEnable(boolean bottomOverScrollEnable) {
        this.bottomOverScrollEnable = bottomOverScrollEnable;
    }

    public boolean isLeftOverScrollEnable() {
        return leftOverScrollEnable;
    }

    /**
     * @param leftOverScrollEnable true can over scroll left false otherwise
     */
    public void setLeftOverScrollEnable(boolean leftOverScrollEnable) {
        this.leftOverScrollEnable = leftOverScrollEnable;
    }

    public boolean isRightOverScrollEnable() {
        return rightOverScrollEnable;
    }

    /**
     * @param rightOverScrollEnable true can over scroll right false otherwise
     */
    public void setRightOverScrollEnable(boolean rightOverScrollEnable) {
        this.rightOverScrollEnable = rightOverScrollEnable;
    }

    public OnOverScrollListener getOnOverScrollListener() {
        return onOverScrollListener;
    }

    /**
     * @param onOverScrollListener listener
     */
    public void setOnOverScrollListener(OnOverScrollListener onOverScrollListener) {
        this.onOverScrollListener = onOverScrollListener;
    }

    public float getFraction() {
        return fraction;
    }

    /**
     * @param fraction the fraction for over scroll.it is num[0f,1f],
     */
    public void setFraction(float fraction) {
        if (fraction >= 0 && fraction <= 1) {
            this.fraction = fraction;
        }
    }

    private void checkCanOverScrollDirection() {
        if (checkScrollDirectionFinish) {
            return;
        }
        if (child instanceof AbsListView || child instanceof ScrollView || child instanceof WebView) {
            canOverScrollHorizontally = false;
            canOverScrollVertical = true;
        } else if (child instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) child;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int mOrientation = -1;
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                mOrientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                mOrientation = manager.getOrientation();
            }
            canOverScrollHorizontally = RecyclerView.HORIZONTAL == mOrientation;
            canOverScrollVertical = RecyclerView.VERTICAL == mOrientation;
        } else if (child instanceof HorizontalScrollView) {
            canOverScrollHorizontally = true;
            canOverScrollVertical = false;
        } else if (child instanceof ViewPager) {
            canOverScrollHorizontally = false;
            canOverScrollVertical = false;
        } else {
            canOverScrollHorizontally = false;
            canOverScrollVertical = true;
        }
        checkScrollDirectionFinish = true;
        if (canOverScrollVertical) {
            baseOverScrollLength = getHeight();
        } else {
            baseOverScrollLength = getWidth();
        }
    }

    @Override public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int scrollerY = mScroller.getCurrY();
            scrollTo(mScroller.getCurrX(), scrollerY);
            postInvalidate();
        } else {
            if (abortScroller) {
                abortScroller = false;
                return;
            }
            if (finishOverScroll) {
                isOverScrollTop = false;
                isOverScrollBottom = false;
                isOverScrollLeft = false;
                isOverScrollRight = false;
                finishOverScroll = false;
            }
        }

    }

    protected void mSmoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        mSmoothScrollBy(dx, dy);
    }


    protected void mSmoothScrollBy(int dx, int dy) {
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();
    }


    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        if (disallowIntercept) {
            return super.dispatchTouchEvent(ev);
        }

        detector.onTouchEvent(ev);

        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                oldY = 0;
                oldX = 0;
                break;
            case MotionEvent.ACTION_DOWN:
                flingRunnable.abort();
                downY = ev.getY();
                oldY = 0;
                dealtY = mScroller.getCurrY();
                if (dealtY == 0) {
                    isVerticalMove = false;
                } else {
                    shouldSetScrollerStart = true;
                    abortScroller = true;
                    mScroller.abortAnimation();
                }

                downX = ev.getX();
                oldX = 0;
                dealtX = mScroller.getCurrX();
                if (dealtX == 0) {
                    isHorizontallyMove = false;
                } else {
                    shouldSetScrollerStart = true;
                    abortScroller = true;
                    mScroller.abortAnimation();
                }
                if (isOverScrollTop || isOverScrollBottom || isOverScrollLeft || isOverScrollRight) {
                    return true;
                }
                checkCanOverScrollDirection();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!canOverScroll()) return super.dispatchTouchEvent(ev);

                if (canOverScrollVertical) {
                    if (isOverScrollTop || isOverScrollBottom) {
                        if (onOverScrollListener != null) {
                            if (isOverScrollTop) {
                                onOverScrollListener.onOverScrollTop();
                            }
                            if (isOverScrollBottom) {
                                onOverScrollListener.onOverScrollBottom();
                            }
                        }
                        if (shouldSetScrollerStart) {
                            shouldSetScrollerStart = false;
                            mScroller.startScroll(dealtX, dealtY, 0, 0);
                        }
                        if (oldY == 0) {
                            oldY = ev.getY();
                            return true;
                        }
                        dealtY += getDealt(oldY - ev.getY(), dealtY);
                        oldY = ev.getY();
                        if (isOverScrollTop && dealtY > 0) {
                            dealtY = 0;
                        }
                        if (isOverScrollBottom && dealtY < 0) {
                            dealtY = 0;
                        }
                        overScroll(dealtX, dealtY);
                        if ((isOverScrollTop && dealtY == 0 && !isOverScrollBottom) || (isOverScrollBottom && dealtY == 0 && !isOverScrollTop)) {
                            oldY = 0;
                            isOverScrollTop = false;
                            isOverScrollBottom = false;
                            return !isChildCanScrollVertical() || super.dispatchTouchEvent(resetVertical(ev));
                        }
                        return true;
                    } else {
                        checkMoveDirection(ev.getX(), ev.getY());
                        if (oldY == 0) {
                            oldY = ev.getY();
                            return true;
                        }
                        boolean tempOverScrollTop = isTopOverScroll(ev.getY());
                        if (!isOverScrollTop && tempOverScrollTop) {
                            oldY = ev.getY();
                            isOverScrollTop = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            super.dispatchTouchEvent(ev);
                            return true;
                        }
                        isOverScrollTop = tempOverScrollTop;
                        boolean tempOverScrollBottom = isBottomOverScroll(ev.getY());
                        if (!isOverScrollBottom && tempOverScrollBottom) {
                            oldY = ev.getY();
                            isOverScrollBottom = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            super.dispatchTouchEvent(ev);
                            return true;
                        }
                        isOverScrollBottom = tempOverScrollBottom;
                        oldY = ev.getY();
                    }
                } else if (canOverScrollHorizontally) {
                    if (isOverScrollLeft || isOverScrollRight) {
                        if (onOverScrollListener != null) {
                            if (isOverScrollLeft) {
                                onOverScrollListener.onOverScrollLeft();
                            }
                            if (isOverScrollRight) {
                                onOverScrollListener.onOverScrollRight();
                            }
                        }
                        if (shouldSetScrollerStart) {
                            shouldSetScrollerStart = false;
                            mScroller.startScroll(dealtX, dealtY, 0, 0);
                        }
                        if (oldX == 0) {
                            oldX = ev.getX();
                            return true;
                        }
                        dealtX += getDealt(oldX - ev.getX(), dealtX);
                        oldX = ev.getX();
                        if (isOverScrollLeft && dealtX > 0) {
                            dealtX = 0;
                        }
                        if (isOverScrollRight && dealtX < 0) {
                            dealtX = 0;
                        }
                        overScroll(dealtX, dealtY);
                        if ((isOverScrollLeft && dealtX == 0 && !isOverScrollRight) || (isOverScrollRight && dealtX == 0 && !isOverScrollLeft)) {
                            oldX = 0;
                            isOverScrollRight = false;
                            isOverScrollLeft = false;
                            return !isChildCanScrollHorizontally() || super.dispatchTouchEvent(resetHorizontally(ev));
                        }
                        return true;
                    } else {
                        checkMoveDirection(ev.getX(), ev.getY());
                        if (oldX == 0) {
                            oldX = ev.getX();
                            return true;
                        }
                        boolean tempOverScrollLeft = isLeftOverScroll(ev.getX());
                        if (!isOverScrollLeft && tempOverScrollLeft) {
                            oldX = ev.getX();
                            isOverScrollLeft = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            super.dispatchTouchEvent(ev);
                            return true;
                        }
                        isOverScrollLeft = tempOverScrollLeft;
                        boolean tempOverScrollRight = isRightOverScroll(ev.getX());
                        if (!isOverScrollRight && tempOverScrollRight) {
                            oldX = ev.getX();
                            isOverScrollRight = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            super.dispatchTouchEvent(ev);
                            return true;
                        }
                        isOverScrollRight = tempOverScrollRight;
                        oldX = ev.getX();
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                oldY = 0;
                oldX = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                finishOverScroll = true;
                mSmoothScrollTo(0, 0);
                break;
        }
        return super.dispatchTouchEvent(ev);

    }

    private float getDealt(float dealt, float distance) {
        if (dealt * distance < 0) return dealt;
        //x 为0的时候 y 一直为0, 所以当x==0的时候,给一个0.1的最小值
        float x = (float) Math.min(Math.max(Math.abs(distance), 0.1) / Math.abs(baseOverScrollLength), 1);
        float y = Math.min(x, 1);
        return dealt * (1 - y);
    }

    private MotionEvent resetVertical(MotionEvent event) {
        oldY = 0;
        dealtY = 0;
        event.setAction(MotionEvent.ACTION_DOWN);
        super.dispatchTouchEvent(event);
        event.setAction(MotionEvent.ACTION_MOVE);
        return event;
    }

    private MotionEvent resetHorizontally(MotionEvent event) {
        oldX = 0;
        dealtX = 0;
        event.setAction(MotionEvent.ACTION_DOWN);
        super.dispatchTouchEvent(event);
        event.setAction(MotionEvent.ACTION_MOVE);
        return event;
    }

    private boolean canOverScroll() {
        return child != null;
    }


    private void overScroll(int dealtX, int dealtY) {
        mSmoothScrollTo((int) (dealtX * fraction), (int) (dealtY * fraction));
    }

    private boolean isTopOverScroll(float currentY) {
        if (isOverScrollTop) {
            return true;
        }
        if (!topOverScrollEnable || !isVerticalMove) {
            return false;
        }
        float dealtY = oldY - currentY;
        return dealtY < 0 && !canChildScrollUp();
    }

    private boolean isBottomOverScroll(float currentY) {
        if (isOverScrollBottom) {
            return true;
        }
        if (!bottomOverScrollEnable || !isVerticalMove) {
            return false;
        }
        float dealtY = oldY - currentY;
        return dealtY > 0 && !canChildScrollDown();
    }

    private boolean isLeftOverScroll(float currentX) {
        if (isOverScrollLeft) {
            return true;
        }
        if (!leftOverScrollEnable || !isHorizontallyMove) {
            return false;
        }
        float dealtX = oldX - currentX;
        return dealtX < 0 && !canChildScrollLeft();
    }

    private boolean isRightOverScroll(float currentX) {
        if (!rightOverScrollEnable || !isHorizontallyMove) {
            return false;
        }
        float dealtX = oldX - currentX;
        return dealtX > 0 && !canChildScrollRight();
    }

    private boolean isChildCanScrollVertical() {
        return canChildScrollDown() || canChildScrollUp();
    }

    private boolean isChildCanScrollHorizontally() {
        return canChildScrollLeft() || canChildScrollRight();
    }

    private void checkMoveDirection(float currentX, float currentY) {
        if (isVerticalMove || isHorizontallyMove) {
            return;
        }
        if (canOverScrollVertical) {
            float dealtY = currentY - downY;
            isVerticalMove = Math.abs(dealtY) >= configuration.getScaledTouchSlop();
        } else if (canOverScrollHorizontally) {
            float dealtX = currentX - downX;
            isHorizontallyMove = Math.abs(dealtX) >= configuration.getScaledTouchSlop();
        }
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * 是否能下拉
     */
    private boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(child, -1);

    }

    /**
     * 是否能上拉
     */
    private boolean canChildScrollDown() {
        if (child instanceof ScrollView && ((ScrollView) child).getChildAt(0) != null) {
            return ((ScrollView) child).getChildAt(0).getHeight() > child.getScrollY() + child.getHeight();
        }
        return ViewCompat.canScrollVertically(child, 1);
    }

    /**
     * 是否能左拉
     */
    private boolean canChildScrollLeft() {
        return ViewCompat.canScrollHorizontally(child, -1);
    }

    /**
     * 是否能右拉
     */
    private boolean canChildScrollRight() {
        return ViewCompat.canScrollHorizontally(child, 1);
    }

    private void startOverScrollAim(float currVelocity) {
        float speed = currVelocity / configuration.getScaledMaximumFlingVelocity();
        if (canOverScrollVertical) {
            if (!canChildScrollUp()) {
                overScrollRunnable.startOverScroll(0, -speed);
            } else {
                overScrollRunnable.startOverScroll(0, speed);
            }
        } else {
            if (canChildScrollRight()) {
                overScrollRunnable.startOverScroll(-speed, 0);
            } else {
                overScrollRunnable.startOverScroll(speed, 0);
            }
        }
    }

    private class OverScrollRunnable implements Runnable {

        private long                DELAY_TIME          = 90;
        private long                duration            = 150;
        private ResetScrollRunnable resetScrollRunnable = new ResetScrollRunnable();
        private float speedX, speedY;
        private long timePass;
        private long startTime;
        private int  distanceX, distanceY;


        void startOverScroll(float speedX, float speedY) {
            this.speedX = speedX;
            this.speedY = speedY;
            startTime = System.currentTimeMillis();
            run();
        }

        @Override public void run() {
            timePass = System.currentTimeMillis() - startTime;
            if (timePass < duration) {
                distanceY = (int) (DELAY_TIME * speedY);
                distanceX = (int) (DELAY_TIME * speedX);
                mSmoothScrollBy(distanceX, distanceY);
                postDelayed(resetScrollRunnable, DELAY_TIME);
            } else if (timePass > duration) {
                mSmoothScrollTo(0, 0);
            }
        }
    }

    private class ResetScrollRunnable implements Runnable {
        @Override public void run() {
            mSmoothScrollTo(0, 0);
        }
    }

    private class FlingRunnable implements Runnable {
        private static final long DELAY_TIME = 30;
        private boolean abort;
        private int mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();

        void startFling(float velocityX, float velocityY) {
            abort = false;
            float velocity = canOverScrollVertical ? velocityY : velocityX;
            overScroller.fling(0, 0, 0, (int) velocity, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            postDelayed(this, DELAY_TIME);
        }

        @Override public void run() {
            if (!abort && overScroller.computeScrollOffset()) {
                boolean scrollEnd;
                if (canOverScrollVertical) {
                    scrollEnd = !canChildScrollDown() || !canChildScrollUp();
                } else {
                    scrollEnd = !canChildScrollLeft() || !canChildScrollRight();
                }

                float currVelocity = overScroller.getCurrVelocity();
                if (scrollEnd) {
                    if (currVelocity > mMinimumFlingVelocity) {
                        startOverScrollAim(currVelocity);
                    }
                } else {
                    if (currVelocity > mMinimumFlingVelocity) {
                        postDelayed(this, DELAY_TIME);
                    }
                }
            }
        }

        void abort() {
            abort = true;
        }
    }
}
