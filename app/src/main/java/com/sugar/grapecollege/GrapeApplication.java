package com.sugar.grapecollege;

import android.support.annotation.Keep;

import com.qsmaxmin.qsbase.QsApplication;
import com.qsmaxmin.qsbase.common.http.QsHttpCallback;
import com.sugar.grapecollege.common.http.CustomHttpCallback;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 11:25
 * @Description
 */
@Keep
public class GrapeApplication extends QsApplication {

    @Override public void onCreate() {
        super.onCreate();
    }

    @Override public boolean isLogOpen() {
        return true;
    }

    /**
     * http全局回调监听
     */
    @Override public QsHttpCallback registerGlobalHttpListener() {
        return new CustomHttpCallback();
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
