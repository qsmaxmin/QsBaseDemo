package com.sugar.grapecollege.web.fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.viewbind.annotation.Bind;
import com.qsmaxmin.qsbase.mvp.fragment.QsFragment;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.widget.progress.ColorProgressBar;
import com.sugar.grapecollege.web.model.WebConstants;
import com.sugar.grapecollege.web.presenter.WebPresenter;


/**
 * @CreateBy qsmaxmin
 * @Date 16/8/24 下午4:30
 * @Description 网页视图
 */
public class WebFragment extends QsFragment<WebPresenter> implements DownloadListener {

    @Bind(R.id.web_view)     WebView          web_view;
    @Bind(R.id.cpb_view) ColorProgressBar cpb_view;
    private                  String           mTitle;
    private boolean timeOut = false;

    /**
     * 以下是加载动画定义的全局变量
     **/
    private ValueAnimator firstAnimation;
    private ValueAnimator nextAnimation;

    /**
     * 初始化
     */
    public static WebFragment getInstance(Bundle bundle) {
        WebFragment mWebFragment = new WebFragment();
        if (bundle != null) mWebFragment.setArguments(bundle);
        return mWebFragment;
    }

    /**
     * 设置布局
     */
    @Override public int layoutId() {
        return R.layout.fragment_webview;
    }

    /**
     * 初始化数据
     */
    @Override public void initData(Bundle savedInstanceState) {
        getPresenter().readData(getArguments());
        initAnimation();
    }

    /**
     * 显示网页
     */
    @SuppressLint("SetJavaScriptEnabled") public void showWeb(String title, String urlOrData, int state) {
        this.mTitle = title;
        setActivityTitle(TextUtils.isEmpty(title) ? getString(R.string.app_name) : title);
//        if (AppUtils.isNetworkConnected()) {
//            // 缓存策略 - 根据cache-control决定是否从网络上取数据
//            web_view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//            /*设置以下两个属性，可以适配图片*/
//            web_view.getSettings().setUseWideViewPort(true);
//            web_view.getSettings().setLoadWithOverviewMode(true);
//        } else {
//            showError();
//            return;
//        }
        web_view.getSettings().setAppCacheEnabled(true);// 设置缓存
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setHorizontalScrollBarEnabled(false);// 滚动条 - 水平不显示
        web_view.setVerticalScrollBarEnabled(false); // 滚动条 - 垂直不显示

        web_view.setWebChromeClient(new MyWebChromeClient());
        web_view.setWebViewClient(new MyWebViewClient());// 设置
        switch (state) {
            default:
            case WebConstants.STATE_URL:
                L.i("WebFragment", "loadUrl data=" + urlOrData);
                web_view.loadUrl(urlOrData);
                break;
            case WebConstants.STATE_DATA:
                L.i("WebFragment", "loadData data=" + urlOrData);
                web_view.loadDataWithBaseURL(null, urlOrData, "text/html", "utf-8", null);
                break;
        }
        web_view.setDownloadListener(this);
        showContentView();
    }

    @Override public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
        L.i("WebFragment", "***********   WebView 请求下载");
        L.i("WebFragment", "***********   url:" + userAgent);
        L.i("WebFragment", "***********   userAgent:" + userAgent);
        L.i("WebFragment", "***********   mimeType:" + mimeType);
        L.i("WebFragment", "***********   contentLength:" + contentLength);
//        DownloadHelper.getInstance().getNormalDownloadHelper().createBuilder().setMimeType(mimeType).setAutoOpen(true).setDownloadUrl(url).setShowInStatus(true).startDownload();
    }


    /**
     * WebView的客户端
     */
    private class MyWebViewClient extends WebViewClient {

        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
            L.i("WebFragment", "shouldOverrideUrlLoading url=" + url);
            return getPresenter().shouldOverrideUrl(web_view, url);
        }

        @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
            displayStartAnimation();
        }

        @Override public void onPageFinished(WebView view, String url) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            displayEndAnimation();
            if (timeOut) {
                return;
            }
            showContentView();
        }

        @Override public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // 忽略htts 证书错误zxzxzx
        }

        @Override public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            switch (errorCode) {
                case ERROR_CONNECT: // 链接服务器断开
                case ERROR_TIMEOUT: // 链接超时
                    showErrorView();
                    timeOut = true;
                    break;
            }
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setActivityTitle(TextUtils.isEmpty(mTitle) ? title : mTitle);
        }
    }


    /**
     * 位置从左到右：-screenWidth, -screenWidth/3, 0
     */
    private void initAnimation() {
        firstAnimation = ValueAnimator.ofFloat(0, .7f).setDuration(1000);
        firstAnimation.setInterpolator(new LinearInterpolator());
        firstAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (cpb_view != null) cpb_view.setRatio(value);
                if (value >= .7f && nextAnimation != null) {
                    if (nextAnimation.isRunning()) nextAnimation.cancel();
                    nextAnimation.setFloatValues(.7f, .95f);
                    nextAnimation.start();
                }
            }
        });

        nextAnimation = ValueAnimator.ofFloat(.7f, .95f).setDuration(10000);
        nextAnimation.setInterpolator(new LinearInterpolator());
        nextAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (cpb_view != null) {
                    cpb_view.setRatio(value);
                    if (value >= 1f) cpb_view.setRatio(0f);
                }
            }
        });
    }

    private void displayStartAnimation() {
        if (firstAnimation != null) {
            if (firstAnimation.isRunning()) firstAnimation.cancel();
            firstAnimation.start();
        }
    }


    private void displayEndAnimation() {
        if (firstAnimation != null && firstAnimation.isRunning()) firstAnimation.cancel();
        if (nextAnimation != null) {
            if (nextAnimation.isRunning()) nextAnimation.cancel();
            if (cpb_view != null) {
                float ratio = cpb_view.getRatio();
                if (ratio < 1f) {
                    nextAnimation.setFloatValues(ratio, 1f);
                    nextAnimation.setDuration((long) (1000 - ratio * 1000));
                    nextAnimation.start();
                } else {
                    cpb_view.setRatio(0f);
                }
            }
        }
    }

    public void performBack() {
        if (web_view == null) {
            return;
        }
        if (web_view.canGoBack()) {
            web_view.goBack();
        } else {
            activityFinish();
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (web_view != null) {
            web_view.destroy();
        }
    }

    @Override public boolean isOpenViewState() {
        return false;
    }
}
