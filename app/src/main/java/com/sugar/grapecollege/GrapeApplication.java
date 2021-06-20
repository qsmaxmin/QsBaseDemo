package com.sugar.grapecollege;


import com.qsmaxmin.qsbase.QsApplication;
import com.qsmaxmin.qsbase.common.http.HttpInterceptor;
import com.qsmaxmin.qsbase.common.utils.ImageHelper;
import com.qsmaxmin.qsbase.common.widget.dialog.QsProgressDialog;
import com.sugar.grapecollege.common.base.BaseActivity;
import com.sugar.grapecollege.common.base.fragment.BaseFragment;
import com.sugar.grapecollege.common.http.CustomHttpInterceptor;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 11:25
 * @Description
 */
public class GrapeApplication extends QsApplication {

    @Override public void onCreate() {
        super.onCreate();
    }

    @Override public boolean isDebug() {
        return true;
    }
    /**
     * http全局拦截器
     */
    @Override public HttpInterceptor registerGlobalHttpInterceptor() {
        return new CustomHttpInterceptor();
    }

    /**
     * 图片加载全局回调
     */
    @Override public void onCommonLoadImage(ImageHelper.Builder builder) {
        super.onCommonLoadImage(builder);
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

    /**
     * 自定义loading窗
     *
     * @see BaseActivity#loading()
     * @see BaseFragment#loading()
     */
    @Override public QsProgressDialog getLoadingDialog() {
        return super.getLoadingDialog();
    }
}
