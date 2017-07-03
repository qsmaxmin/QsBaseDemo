package com.sugar.grapecollege.common.widget.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.View;

import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/8 18:32
 * @Description
 */

public class ColorProgressBar extends View {

    private int[]   colorArr = new int[3];
    private float[] ratios   = new float[3];

    private LinearGradient linearGradient;
    private float          ratio;
    private int            color;
    private RectF          rectF;
    private Paint          paint;

    public ColorProgressBar(Context context) {
        super(context);
        init();
    }

    public ColorProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        color = getResources().getColor(R.color.colorAccent);
        colorArr[0] = Color.TRANSPARENT;
        colorArr[1] = color;
        colorArr[2] = Color.TRANSPARENT;
        ratios[0] = 0f;
        ratios[1] = 0f;
        ratios[2] = 0f;
        linearGradient = new LinearGradient(0, 0, 0, 0, colorArr, ratios, Shader.TileMode.CLAMP);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setProgressColor(int color) {
        this.color = color;
        colorArr[0] = ColorUtils.blendARGB(color, Color.TRANSPARENT, ratio);
        colorArr[1] = color;
        linearGradient = new LinearGradient(0, 0, getWidth(), 0, colorArr, ratios, Shader.TileMode.CLAMP);
        invalidateView();
    }

    public void setRatio(float ratio) {
        ratio = ratio < 0f ? 0 : (ratio > 1f ? 1 : ratio);
        this.ratio = ratio;
        ratios[1] = ratio;
        ratios[2] = ratio;
        colorArr[0] = ColorUtils.blendARGB(color, Color.TRANSPARENT, ratio);
        linearGradient = new LinearGradient(0, 0, getWidth(), 0, colorArr, ratios, Shader.TileMode.CLAMP);
        invalidateView();
    }

    public float getRatio() {
        return this.ratio;
    }

    @Override protected void onDraw(Canvas canvas) {
        paint.setShader(linearGradient);
        canvas.drawRect(getRectF(), paint);
    }

    private RectF getRectF() {
        if (rectF == null) {
            rectF = new RectF(0, 0, getWidth(), getHeight());
        } else {
            rectF.set(0, 0, getWidth(), getHeight());
        }
        return rectF;
    }

    private void invalidateView() {
        if (isChildThread()) postInvalidate();
        else invalidate();
    }

    private boolean isChildThread() {
        return Thread.currentThread() != Looper.getMainLooper().getThread();
    }
}
