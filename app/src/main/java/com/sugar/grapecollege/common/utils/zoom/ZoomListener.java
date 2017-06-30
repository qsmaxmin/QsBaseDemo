package com.sugar.grapecollege.common.utils.zoom;

import android.view.View;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/27 10:41
 * @Description
 */

public interface ZoomListener {
    void onClick(View v);

    void onViewStartedZooming(View view);

    void onViewEndedZooming(View view);
}
