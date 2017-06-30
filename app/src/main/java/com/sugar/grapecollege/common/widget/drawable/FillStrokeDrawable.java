package com.sugar.grapecollege.common.widget.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/1 9:41
 * @Description
 */

public class FillStrokeDrawable extends Drawable {

    private Paint strokePaint;
    private Paint fillPaint;
    private float density;

    @Override public void setAlpha(int alpha) {

    }

    @Override public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public FillStrokeDrawable(int fillColor, int strokeColor) {
        density = 1f;
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(fillColor);
        fillPaint.setStyle(Paint.Style.FILL);

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(strokeColor);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(density);
    }


    @Override public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        canvas.drawRect(bounds, fillPaint);
        canvas.drawRect(bounds.left + density / 2, bounds.top + density / 2, bounds.right - density / 2, bounds.bottom - density / 2, strokePaint);
    }
}
