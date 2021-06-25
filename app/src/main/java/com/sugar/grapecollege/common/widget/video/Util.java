package com.sugar.grapecollege.common.widget.video;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.google.android.exoplayer2.C;

import java.util.Formatter;

import androidx.core.content.res.ResourcesCompat;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/6/25 13:45
 * @Description
 */
class Util {
    static String getStringForTime(StringBuilder builder, Formatter formatter, long timeMs) {
        if (timeMs == C.TIME_UNSET) {
            timeMs = 0;
        }
        String prefix = timeMs < 0 ? "-" : "";
        timeMs = abs(timeMs);
        long totalSeconds = (timeMs + 500) / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        builder.setLength(0);
        return hours > 0
                ? formatter.format("%s%d:%02d:%02d", prefix, hours, minutes, seconds).toString()
                : formatter.format("%s%02d:%02d", prefix, minutes, seconds).toString();
    }

    static long constrainValue(long value, long min, long max) {
        return max(min, min(value, max));
    }

    static Drawable getDrawable(Resources resources, int resId) {
        return ResourcesCompat.getDrawable(resources, resId, null);
    }

    static final int SDK_INT =
            "S".equals(Build.VERSION.CODENAME)
                    ? 31
                    : "R".equals(Build.VERSION.CODENAME) ? 30 : Build.VERSION.SDK_INT;
}
