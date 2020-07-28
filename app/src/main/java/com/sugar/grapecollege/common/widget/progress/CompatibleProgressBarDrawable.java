package com.sugar.grapecollege.common.widget.progress;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/12 10:44
 * @Description
 */

class CompatibleProgressBarDrawable extends Drawable {

    private float strokeWidth;
    private Path  path;
    private float radius;
    private Paint fillPaint;
    private Paint unFillPaint;
    private Paint strokePaint;
    private float ratio;

    CompatibleProgressBarDrawable(float radius, int fillColor, int unFillColor, int strokeColor, float strokeWidth) {
        this.radius = radius;
        this.strokeWidth = strokeWidth;
        this.path = new Path();
        this.fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.fillPaint.setColor(fillColor);

        this.unFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.unFillPaint.setColor(unFillColor);

        this.strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.strokePaint.setColor(strokeColor);
        this.strokePaint.setStyle(Paint.Style.STROKE);
        if (strokeWidth > 0) this.strokePaint.setStrokeWidth(strokeWidth);

    }

    void setRatio(float ratio) {
        if (this.ratio != ratio) {
            this.ratio = ratio;
            invalidateSelf();
        }
    }

    @Override public void draw(@NonNull Canvas canvas) {
        float[] radiusArr = new float[8];
        Rect rect = getBounds();
        float width = rect.width();
        RectF innerRectF = new RectF(rect.left + strokeWidth, rect.top + strokeWidth, rect.right - strokeWidth, rect.bottom - strokeWidth);
        float innerRadius = radius - strokeWidth;
        ratio = ratio < 0 ? 0 : (ratio > 1f ? 1f : ratio);

        if (ratio == 0) {
            canvas.drawRoundRect(innerRectF, innerRadius, innerRadius, unFillPaint);
        } else if (ratio == 1f) {
            canvas.drawRoundRect(innerRectF, innerRadius, innerRadius, fillPaint);
        } else {
            float innerWidth = width - strokeWidth * 2;
            float leftWidth = innerWidth * ratio;
            if (leftWidth < innerRadius) {
                canvas.drawRoundRect(innerRectF, innerRadius, innerRadius, unFillPaint);
                float pointYTop = (float) (innerRadius - Math.sqrt(innerRadius * innerRadius - (leftWidth - innerRadius) * (leftWidth - innerRadius)));
                RectF rectLeft = new RectF(innerRectF.left, pointYTop + strokeWidth, leftWidth + strokeWidth, innerRectF.bottom - pointYTop);
                Path path = getPath();
                setRadiusArr(radiusArr, leftWidth, innerRadius - pointYTop, 0, 0, 0, 0, leftWidth, innerRadius - pointYTop);
                path.addRoundRect(rectLeft, radiusArr, Path.Direction.CCW);
                canvas.drawPath(path, fillPaint);

            } else if (leftWidth >= innerRadius && leftWidth <= innerWidth - innerRadius) {
                canvas.drawRoundRect(innerRectF, innerRadius, innerRadius, unFillPaint);
                setRadiusArr(radiusArr, innerRadius, innerRadius, 0, 0, 0, 0, innerRadius, innerRadius);
                RectF rectLeft = new RectF(innerRectF.left, innerRectF.top, leftWidth + strokeWidth, innerRectF.bottom);
                Path path = getPath();
                path.addRoundRect(rectLeft, radiusArr, Path.Direction.CCW);
                canvas.drawPath(path, fillPaint);

            } else if (leftWidth > innerWidth - innerRadius && leftWidth <= innerWidth) {
                canvas.drawRoundRect(innerRectF, innerRadius, innerRadius, fillPaint);

                float length = innerWidth - leftWidth;
                float pointYTop = (float) (innerRadius - Math.sqrt(innerRadius * innerRadius - (length - innerRadius) * (length - innerRadius)));

                setRadiusArr(radiusArr, 0, 0, length, innerRadius - pointYTop, length, innerRadius - pointYTop, 0, 0);
                RectF rectRight = new RectF(leftWidth, pointYTop, innerRectF.right, innerRectF.bottom - pointYTop);
                Path path = getPath();
                path.addRoundRect(rectRight, radiusArr, Path.Direction.CCW);
                canvas.drawPath(path, unFillPaint);
            }
        }
        RectF rectStroke = new RectF(rect.left + strokeWidth / 2, rect.top + strokeWidth / 2, rect.right - strokeWidth / 2, rect.bottom - strokeWidth / 2);
        if (strokeWidth > 0) canvas.drawRoundRect(rectStroke, radius, radius, strokePaint);
    }

    private void setRadiusArr(float[] radiusArr, float f0, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
        radiusArr[0] = f0;
        radiusArr[1] = f1;
        radiusArr[2] = f2;
        radiusArr[3] = f3;
        radiusArr[4] = f4;
        radiusArr[5] = f5;
        radiusArr[6] = f6;
        radiusArr[7] = f7;
    }

    @Override public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private Path getPath() {
        path.rewind();
        return path;
    }
}
