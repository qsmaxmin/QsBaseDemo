package com.sugar.grapecollege.common.widget.viewpager;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/2/7 14:41
 * @Description viewpager转换器 移动child位置
 */
public class TranslatePageTransformer implements ViewPager.PageTransformer {

    private float DISTANCE_Y;

    public TranslatePageTransformer() {
        this(30);
    }

    public TranslatePageTransformer(float distance) {
        this.DISTANCE_Y = distance;
    }

    @Override public void transformPage(View page, float position) {
        position = position < -1 ? -1f : (position > 1f ? 1f : position);
        if (position < 0) {
            page.setTranslationY(position * DISTANCE_Y);
        } else {
            page.setTranslationY(position * -DISTANCE_Y);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            page.getParent().requestLayout();
        }
    }
}
