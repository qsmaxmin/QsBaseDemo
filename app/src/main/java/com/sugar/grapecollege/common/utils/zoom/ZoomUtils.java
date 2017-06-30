package com.sugar.grapecollege.common.utils.zoom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/27 10:31
 * @Description
 */

class ZoomUtils {
    /**
     * 计算两点之间的中点
     */
    static void midPointOfEvent(PointF point, MotionEvent event) {
        if (event.getPointerCount() == 2) {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
        }
    }

    static Bitmap getBitmapFromView(View view) {
        Bitmap result = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) bgDrawable.draw(canvas);
        else canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return result;
    }

    /**
     * 获取view在屏幕的位置
     */
    static Point getViewAbsolutePoint(View v) {
        int[] location = new int[2];
        v.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        return new Point(x, y);
    }

    static void viewMidPoint(PointF point, View v) {
        float x = v.getWidth();
        float y = v.getHeight();
        point.set(x / 2, y / 2);
    }
}
