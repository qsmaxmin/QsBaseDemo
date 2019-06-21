package com.sugar.grapecollege;

import android.support.annotation.Keep;

import com.qsmaxmin.qsbase.QsApplication;
import com.qsmaxmin.qsbase.common.http.HttpAdapter;
import com.qsmaxmin.qsbase.common.http.HttpBuilder;
import com.qsmaxmin.qsbase.common.http.QsHttpCallback;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.common.model.AppConstants;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 11:25
 * @Description
 */
@Keep
public class GrapeApplication extends QsApplication {

    @Override public void onCreate() {
        super.onCreate();
        QsHelper.getInstance().enableFastBindView();
        /*内存泄漏检测工具*/
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
    }

    @Override public boolean isLogOpen() {
        return true;
    }

    @Override public QsHttpCallback registerGlobalHttpListener() {
        return super.registerGlobalHttpListener();
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
