package com.font.common.widget.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.qsmaxmin.qsbase.common.log.L;
import com.sugar.grapecollege.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GestureDetectorCompat;


/**
 * @CreateBy qsmaxmin
 * @Date 2019/11/4 13:39
 * @Description A picture editing widget
 */
public class PhotoView extends AppCompatImageView {
    private RectF                 cutRect;
    private Bitmap                originalBitmap;
    private RectF                 originalRect;
    private Matrix                originalMatrix;
    private RectF                 bitmapRect;
    private Matrix                bitmapMatrix;
    private GestureDetectorCompat touchDetector;
    private ScaleGestureDetector  scaleDetector;
    private RecoverRunnable       recoverRunnable;
    private FlingRunnable         flingRunnable;
    private ResetRunnable         resetRunnable;
    private TapScaleRunnable      tapScaleRunnable;
    private float                 lastAngle;
    private boolean               isIdle;
    private float                 dimensionRatio;

    public PhotoView(Context context) {
        super(context);
        init(null);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        dimensionRatio = 1f;
        isIdle = true;
        cutRect = new RectF();
        originalRect = new RectF();
        originalMatrix = new Matrix();
        bitmapRect = new RectF();
        bitmapMatrix = new Matrix();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PhotoView);
            String ratioText = typedArray.getString(R.styleable.PhotoView_dimensionRatio);
            if (ratioText != null) {
                processDimensionRatio(ratioText);
            }
            typedArray.recycle();
        }

        scaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
            @Override public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                bitmapMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                mapWithOriginalRect(bitmapMatrix, bitmapRect);
                invalidate();
                return true;
            }

            @Override public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override public void onScaleEnd(ScaleGestureDetector detector) {
                startRecover();
            }
        });

        touchDetector = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                int pointerCount = e2.getPointerCount();
                if (pointerCount == 1) {
                    bitmapMatrix.postTranslate(-distanceX, -distanceY);
                    mapWithOriginalRect(bitmapMatrix, bitmapRect);
                    invalidate();
                }
                return pointerCount == 1;
            }

            @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                startFling(velocityX, velocityY);
                return true;
            }

            @Override public boolean onDown(MotionEvent e) {
                isIdle = false;
                stopFling();
                return true;
            }

            @Override public boolean onDoubleTap(MotionEvent e) {
                float scale = bitmapRect.width() / cutRect.width();
                float maxScale = 4f;
                if (scale < maxScale) {
                    scale *= 1.5f;
                    if (scale > maxScale) scale = maxScale;
                    startTapScale(scale, e.getX(), e.getY());
                } else {
                    float sc = cutRect.width() / bitmapRect.width();
                    startTapScale(sc, e.getX(), e.getY());
                }
                return true;
            }
        });
    }

    private void processDimensionRatio(String ratioText) {
        String[] split = ratioText.split(":");
        if (split.length == 2) {
            try {
                float width = Float.parseFloat(split[0]);
                float height = Float.parseFloat(split[1]);
                if (width > 0 && height > 0) {
                    dimensionRatio = width / height;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置旋转角度
     *
     * @param end 如果为true则旋转完图片后检测图片大小及边界，若大小或边界不符合要求则缩放或移动图片
     */
    public void setAngle(float angle, boolean end) {
        if (originalBitmap != null) {
            if (angle != lastAngle) {
                bitmapMatrix.postRotate(angle - lastAngle, getWidth() / 2f, getHeight() / 2f);
                mapWithOriginalRect(bitmapMatrix, bitmapRect);
                lastAngle = angle;
                invalidate();
            }
            if (end) startRecover();
        }
    }

    /**
     * 重置旋转，缩放，平移到初始状态
     */
    public void reset() {
        if (originalBitmap != null) resetInner(true);
    }

    public void reset(boolean anim) {
        if (originalBitmap != null) resetInner(anim);
    }

    /**
     * 根据裁剪区域纹理生成一个Bitmap
     *
     * @return 裁切后的Bitmap，如果手指未离开该控件或者该控件变换动画未执行完成，返回null
     */
    public Bitmap getBitmap() {
        if (originalBitmap != null && isIdle) {
            Bitmap bitmap = Bitmap.createBitmap((int) cutRect.width(), (int) cutRect.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(originalBitmap, bitmapMatrix, null);
            return bitmap;
        }
        return null;
    }

    @Override public void setImageResource(int resId) {
        Bitmap bitmap = null;
        if (resId != 0) {
            bitmap = BitmapFactory.decodeResource(getResources(), resId);
        }
        setImageBitmapInner(bitmap);
    }

    @Override public void setImageDrawable(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        setImageBitmapInner(bitmap);
    }

    @Override public void setImageURI(Uri uri) {
        if (uri != null) {
            try {
                InputStream is = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                setImageBitmap(bitmap);
            } catch (Exception e) {
                setImagePath(null);
                e.printStackTrace();
            }
        } else {
            setImagePath(null);
        }
    }

    @Override public void setImageBitmap(Bitmap bitmap) {
        setImageBitmapInner(bitmap);
    }

    public void setImagePath(String path) {
        Bitmap bitmap = null;
        if (!TextUtils.isEmpty(path)) {
            try {
                FileInputStream fis = new FileInputStream(path);
                bitmap = BitmapFactory.decodeStream(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setImageBitmapInner(bitmap);
    }

    private void setImageBitmapInner(Bitmap bitmap) {
        if (bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
            float screenWidth = getResources().getDisplayMetrics().widthPixels;
            float screenHeight = getResources().getDisplayMetrics().heightPixels;
            float scaleX = bitmap.getWidth() / screenWidth;
            float scaleY = bitmap.getHeight() / screenHeight;
            float scaleMax = Math.max(scaleX, scaleY);
            if (scaleMax > 1.5f) {
                int newWidth = (int) (bitmap.getWidth() / scaleMax);
                int newHeight = (int) (bitmap.getHeight() / scaleMax);
                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
                if (L.isEnable()) L.i("MatrixImageView", "setImageBitmapInner....bitmap size is too big, scale to:" + newWidth + ", " + newHeight);
            }
        }
        this.originalBitmap = bitmap;
        initBitmapRect();
        invalidate();
    }

    private void initBitmapRect() {
        if (originalBitmap != null && cutRect.width() > 0 && cutRect.height() > 0) {
            int bw = originalBitmap.getWidth();
            int bh = originalBitmap.getHeight();
            float cw = cutRect.width();
            float ch = cutRect.height();
            float scaleX = cw / bw;
            float scaleY = ch / bh;
            float maxScale = Math.max(scaleX, scaleY);
            float newW = bw * maxScale;
            float newH = bh * maxScale;
            float left = (cw - newW) / 2f;
            float top = (ch - newH) / 2f;

            originalRect.set(0, 0, originalBitmap.getWidth(), originalBitmap.getHeight());
            originalMatrix.reset();
            originalMatrix.postScale(maxScale, maxScale);
            originalMatrix.postTranslate(left, top);
            bitmapMatrix.set(originalMatrix);
            mapWithOriginalRect(bitmapMatrix, bitmapRect);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override public boolean onTouchEvent(MotionEvent event) {
        if (originalBitmap == null || touchDetector.onTouchEvent(event)) return true;
        scaleDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!isInFling() && !isInTapScaling()) startRecover();
        }
        return true;
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        float ratio = width * 1f / height;
        if (dimensionRatio < ratio) {
            width = (int) (height * dimensionRatio);
        } else if (dimensionRatio > ratio) {
            height = (int) (width / dimensionRatio);
        }
        cutRect.set(0, 0, width, height);
        initBitmapRect();

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void onDraw(Canvas canvas) {
        if (originalBitmap != null) {
            canvas.drawBitmap(originalBitmap, bitmapMatrix, null);
        }
    }

    private void mapWithOriginalRect(Matrix matrix, RectF rectF) {
        matrix.mapRect(rectF, originalRect);
    }

    private void startFling(float velocityX, float velocityY) {
        if ((velocityX != 0 || velocityY != 0) && bitmapRect.contains(cutRect)) {
            if (flingRunnable == null) {
                flingRunnable = new FlingRunnable();
            }
            flingRunnable.fling((int) velocityX, (int) velocityY);
        } else {
            startRecover();
        }
    }

    private void stopFling() {
        if (flingRunnable != null) flingRunnable.stopFling();
    }

    private void startRecover() {
        if (recoverRunnable == null) recoverRunnable = new RecoverRunnable();
        recoverRunnable.recover();
    }

    private boolean isInFling() {
        return flingRunnable != null && flingRunnable.isFling();
    }

    private boolean isInTapScaling() {
        return tapScaleRunnable != null && tapScaleRunnable.isScaling();
    }

    private void resetInner(boolean anim) {
        if (resetRunnable == null) {
            resetRunnable = new ResetRunnable();
        }
        resetRunnable.resetMatrix(anim);
    }

    private void startTapScale(float scaleFactor, float px, float py) {
        if (tapScaleRunnable == null) tapScaleRunnable = new TapScaleRunnable();
        tapScaleRunnable.tapScale(scaleFactor, px, py);
    }

    private void postAnimation(Runnable action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postOnAnimation(action);
        } else {
            postDelayed(action, 16L);
        }
    }

    public void setDimensionRatio(float dimensionRatio) {
        if (this.dimensionRatio != dimensionRatio) {
            this.dimensionRatio = dimensionRatio;
            requestLayout();
        }
    }

    private class FlingRunnable implements Runnable {
        private Scroller scroller;
        private Matrix   tempMatrix;
        private boolean  isFling;

        FlingRunnable() {
            scroller = new Scroller(getContext());
            tempMatrix = new Matrix();
        }

        @Override public void run() {
            if (!scroller.isFinished() && scroller.computeScrollOffset()) {
                int currX = scroller.getCurrX();
                int currY = scroller.getCurrY();
                bitmapMatrix.set(tempMatrix);
                bitmapMatrix.postTranslate(currX, currY);
                mapWithOriginalRect(bitmapMatrix, bitmapRect);
                invalidate();
                postAnimation(this);
            } else {
                isFling = false;
                startRecover();
            }
        }

        private void fling(int velocityX, int velocityY) {
            scroller.forceFinished(true);
            int minX = 0, maxX = 0, minY = 0, maxY = 0;
            if (bitmapRect.left < cutRect.left && velocityX > 0) {
                maxX = (int) (cutRect.left - bitmapRect.left);
            } else if (bitmapRect.right > cutRect.right && velocityX < 0) {
                minX = (int) (cutRect.right - bitmapRect.right);
            }

            if (bitmapRect.top < cutRect.top && velocityY > 0) {
                maxY = (int) (cutRect.top - bitmapRect.top);
            } else if (bitmapRect.bottom > cutRect.bottom && velocityY < 0) {
                minY = (int) (cutRect.bottom - bitmapRect.bottom);
            }
            if (minX != 0 || maxX != 0 || minY != 0 || maxY != 0) {
                isFling = true;
                scroller.fling(0, 0, velocityX, velocityY, minX, maxX, minY, maxY);
                tempMatrix.set(bitmapMatrix);
                postAnimation(this);
            } else {
                startRecover();
            }
        }

        private void stopFling() {
            scroller.forceFinished(true);
        }

        private boolean isFling() {
            return isFling;
        }
    }

    private class RecoverRunnable implements Runnable {
        private final Interpolator interpolator;
        private final Matrix       tempMatrix;
        private final RectF        tempRect;
        private final float        stepValue;
        private       float        progress;
        private       float        translateX;
        private       float        translateY;
        private       float        scaleStart;
        private       float        scaleEnd;
        private       float[]      values;

        private RecoverRunnable() {
            stepValue = 0.04f;
            values = new float[9];
            interpolator = new DecelerateInterpolator(2f);
            tempMatrix = new Matrix();
            tempRect = new RectF();
        }

        @Override public void run() {
            if (progress <= 1f) {
                progress += stepValue;
                float value = interpolator.getInterpolation(progress);
                bitmapMatrix.set(tempMatrix);
                if (scaleEnd != 0) {
                    float scale = 1f + (scaleEnd / scaleStart - 1f) * value;
                    bitmapMatrix.postScale(scale, scale, tempRect.centerX(), tempRect.centerY());
                }
                bitmapMatrix.postTranslate(translateX * value, translateY * value);
                mapWithOriginalRect(bitmapMatrix, bitmapRect);
                invalidate();
                postAnimation(this);
            } else {
                isIdle = true;
            }
        }

        private void recover() {
            removeCallbacks(this);
            stopFling();
            progress = 0f;
            scaleEnd = 0;
            translateX = 0;
            translateY = 0;
            tempMatrix.set(bitmapMatrix);
            tempRect.set(bitmapRect);

            tempMatrix.getValues(values);
            scaleStart = values[Matrix.MSCALE_X];
            if (tempRect.width() < cutRect.width() || tempRect.height() < cutRect.height()) {
                float scaleX = cutRect.width() / tempRect.width();
                float scaleY = cutRect.height() / tempRect.height();
                float maxScale = Math.max(scaleX, scaleY);
                tempMatrix.postScale(maxScale, maxScale, tempRect.centerX(), tempRect.centerY());
                mapWithOriginalRect(tempMatrix, tempRect);
                tempMatrix.getValues(values);
                scaleEnd = values[Matrix.MSCALE_X];
                tempMatrix.set(bitmapMatrix);
            }

            if (tempRect.left > cutRect.left) {
                translateX = cutRect.left - tempRect.left;
            } else if (tempRect.right < cutRect.right) {
                translateX = cutRect.right - tempRect.right;
            }

            if (tempRect.top > cutRect.top) {
                translateY = cutRect.top - tempRect.top;
            } else if (tempRect.bottom < cutRect.bottom) {
                translateY = cutRect.bottom - tempRect.bottom;
            }
            if (scaleEnd != 0 || translateX != 0 || translateY != 0) {
                postAnimation(this);
            } else {
                isIdle = true;
            }
        }
    }

    private class ResetRunnable implements Runnable {
        private Matrix       tempMatrix;
        private float        progress;
        private float        stepValue;
        private float[]      sv;
        private float[]      ev;
        private float[]      cv;
        private Interpolator interpolator;

        private ResetRunnable() {
            stepValue = 0.04f;
            sv = new float[9];
            ev = new float[9];
            cv = new float[9];
            tempMatrix = new Matrix();
            interpolator = new DecelerateInterpolator(2f);
        }

        @Override public void run() {
            if (progress <= 1f) {
                progress += stepValue;
                float value = interpolator.getInterpolation(progress);
                for (int i = 0; i < 6; i++) {
                    cv[i] = sv[i] + (ev[i] - sv[i]) * value;
                }
                bitmapMatrix.setValues(cv);
                mapWithOriginalRect(bitmapMatrix, bitmapRect);
                invalidate();
                postAnimation(this);
            } else {
                resetFinal();
            }
        }

        private void resetMatrix(boolean anim) {
            if (anim) {
                removeCallbacks(this);
                progress = 0f;
                isIdle = false;
                tempMatrix.set(bitmapMatrix);
                tempMatrix.getValues(sv);
                originalMatrix.getValues(ev);
                System.arraycopy(sv, 0, cv, 0, cv.length);
                postAnimation(this);
            } else {
                resetFinal();
            }
        }

        private void resetFinal() {
            isIdle = true;
            lastAngle = 0;
            bitmapMatrix.set(originalMatrix);
            mapWithOriginalRect(bitmapMatrix, bitmapRect);
            invalidate();
        }
    }

    private class TapScaleRunnable implements Runnable {
        private Matrix       tempMatrix;
        private float        progress;
        private float        stepValue;
        private boolean      isRunning;
        private Interpolator interpolator;
        private float[]      sv;
        private float[]      ev;
        private float[]      cv;

        private TapScaleRunnable() {
            tempMatrix = new Matrix();
            stepValue = 0.04f;
            sv = new float[9];
            ev = new float[9];
            cv = new float[9];
            interpolator = new DecelerateInterpolator(2f);
        }

        @Override public void run() {
            if (progress <= 1f) {
                progress += stepValue;
                float value = interpolator.getInterpolation(progress);
                for (int i = 0; i < 6; i++) {
                    cv[i] = sv[i] + (ev[i] - sv[i]) * value;
                }
                bitmapMatrix.setValues(cv);
                mapWithOriginalRect(bitmapMatrix, bitmapRect);
                invalidate();
                postAnimation(this);
            } else {
                isRunning = false;
                startRecover();
            }
        }

        private void tapScale(float scale, float px, float py) {
            isIdle = false;
            isRunning = true;
            removeCallbacks(this);
            progress = 0;
            tempMatrix.set(bitmapMatrix);
            tempMatrix.getValues(sv);
            System.arraycopy(sv, 0, cv, 0, cv.length);
            tempMatrix.postScale(scale, scale, px, py);
            tempMatrix.getValues(ev);
            postAnimation(this);
        }

        private boolean isScaling() {
            return isRunning;
        }
    }
}
