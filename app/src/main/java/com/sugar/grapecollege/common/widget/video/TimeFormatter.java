package com.sugar.grapecollege.common.widget.video;

import com.google.android.exoplayer2.C;

import java.util.Formatter;
import java.util.Locale;

import static java.lang.Math.abs;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/6/25 13:51
 * @Description
 */
class TimeFormatter {
    private final StringBuilder formatBuilder;
    private final Formatter     formatter;

    TimeFormatter() {
        formatBuilder = new StringBuilder();
        formatter = new Formatter(formatBuilder, Locale.getDefault());
    }

    String getStringForTime(long timeMs) {
        if (timeMs == C.TIME_UNSET) {
            timeMs = 0;
        }
        String prefix = timeMs < 0 ? "-" : "";
        timeMs = abs(timeMs);
        long totalSeconds = (timeMs + 500) / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        formatBuilder.setLength(0);
        return hours > 0
                ? formatter.format("%s%d:%02d:%02d", prefix, hours, minutes, seconds).toString()
                : formatter.format("%s%02d:%02d", prefix, minutes, seconds).toString();
    }
}
