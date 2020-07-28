package com.sugar.grapecollege.common.widget.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/2 16:45
 * @Description 可设置进度的textView
 */

public class ProgressTextView extends AppCompatTextView {

    private float          mRatio;
    private int            widthSize;
    private int            heightSize;
    private LinearGradient linearGradient;

    private int[]   colors = new int[4];
    private float[] ratios = new float[]{0f, 0f, 0f, 1f};


    public ProgressTextView(Context ctx) {
        this(ctx, null);
    }

    public ProgressTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setStartColor(int color) {
        this.colors[0] = color;
        this.colors[1] = color;
        invalidateView();
    }

    public void setEndColor(int color) {
        this.colors[2] = color;
        this.colors[3] = color;
        invalidateView();
    }

    public void setRatio(float ratio) {
        if (mRatio != ratio && ratio >= 0f && ratio <= 1f) {
            refreshView(ratio);
        }
    }

    private void refreshView(float ratio) {
        this.mRatio = ratio;
        if (widthSize > 0 && heightSize > 0) {
            this.ratios[1] = ratio - 0.001f;
            this.ratios[2] = ratio;
            linearGradient = new LinearGradient(0, heightSize / 2, widthSize, heightSize / 2, colors, ratios, Shader.TileMode.CLAMP);
            invalidateView();
        }
    }

    private void invalidateView() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthSize = w;
        heightSize = h;
        refreshView(mRatio);
    }

    @Override protected void onDraw(Canvas canvas) {
        if (linearGradient != null) getPaint().setShader(linearGradient);
        super.onDraw(canvas);
    }
}
