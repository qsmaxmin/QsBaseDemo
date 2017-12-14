package com.sugar.grapecollege;

import com.qsmaxmin.qsbase.QsApplication;
import com.qsmaxmin.qsbase.common.http.HttpAdapter;
import com.qsmaxmin.qsbase.common.http.HttpBuilder;
import com.sugar.grapecollege.common.model.AppConstants;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 11:25
 * @Description
 */

public class GrapeApplication extends QsApplication {

    @Override public void onCreate() {
        super.onCreate();
//        DownloadHelper.getInstance().init(this, DownloadConstants.MAX_DOWNLOAD_COUNT);

        /*内存泄漏检测工具*/
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
    }

    @Override public boolean isLogOpen() {
        return true;
    }

    /**
     * 给http请求设置参数
     */
    @Override public void initHttpAdapter(HttpBuilder httpBuilder) {
        httpBuilder.setTerminal("http://www.baidu.com");
        httpBuilder.addHeader("Content-Type", "application/json");
        httpBuilder.addHeader("os", AppConstants.APP_OS);
        httpBuilder.addHeader("bundleId", AppConstants.PACKAGE_NAME);
    }



    /**
     * 页面为空的布局
     */
    @Override public int emptyLayoutId() {
        return R.layout.fragment_common_empty;
    }

    /**
     * 页面出现错误的布局
     */
    @Override public int errorLayoutId() {
        return R.layout.fragment_common_httperror;
    }

    /**
     * 页面正在加载时的布局
     */
    @Override public int loadingLayoutId() {
        return R.layout.fragment_common_loading;
    }

}
