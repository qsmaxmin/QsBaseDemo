package com.sugar.grapecollege.common.utils;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.web.WebViewActivity;
import com.sugar.grapecollege.web.model.WebConstants;


/**
 * deepLink链接跳转，包括内部WebVeiw，第三方浏览器等跳转
 */
public class DeepLinkUtils {

    private static final String LOGIN    = "/login";//登录
    private static final String WEB_VIEW = "/webview";//网页，bannerValue为url
    private static final String DOWNLOAD = "/download";//下载

    /**
     * 适用于原生内部跳转
     *
     * @param type  跳转类型，用于区分跳转哪个页面
     * @param value 跳转时带的参数
     */
    public static boolean applyLink(String type, String... value) {
        return apply(type, value);
    }

    /**
     * 适用于H5及外部跳转native
     */
    public static boolean applyLink(Uri uri) {
        if (uri == null) {
            return false;
        }
        L.i("DeepLinkUtils","***********DeepLinkUtils url:" + uri.toString());
        String path = uri.getPath();
        String value = getParamValue(uri, "id");
        return apply(path, value);
    }

    /**
     * 解析执行
     */
    private static boolean apply(String type, String... value) {
        L.i("DeepLinkUtils","***********DeepLinkUtils path:" + type);
        if (TextUtils.isEmpty(type)) {
            L.e("DeepLinkUtils","**************  跳转类型和url不可为空");
            return false;
        }
        switch (type) {
            case LOGIN:
                intent2Login(type);
                return true;
            case WEB_VIEW:
                intent2WebView(value);
                return true;
            case DOWNLOAD:
                requestDownload(value);
                return true;
            default:
                return false;
        }
    }

    private static void requestDownload(String[] value) {
        if (value.length > 0 && !TextUtils.isEmpty(value[0])) {
//            DownloadHelper.getInstance().getNormalDownloadHelper().createBuilder().setAutoOpen(true).setDownloadUrl(value[0]).setShowInStatus(true).startDownload();
        } else {
//            J2WToast.show(QsHelper.getInstance().getString(R.string.parameter_lose));
        }
    }

    private static void intent2WebView(String[] value) {
        if (value.length > 0 && !TextUtils.isEmpty(value[0])) {
            String url = value[0];
            L.i("DeepLinkUtils","intent2WebView  url:" + url);
            Bundle bundle = new Bundle();
            bundle.putString(WebConstants.BUNDLE_URL_KEY, url);
            QsHelper.getInstance().intent2Activity(WebViewActivity.class, bundle);
        } else {
//            J2WToast.show(QsHelper.getInstance().getString(R.string.parameter_lose));
        }
    }

    private static void intent2Login(String type) {
    }

    /**
     * 获取Uri的参数值
     */
    private static String getParamValue(Uri uri, String key) {
        String queryParameter = uri.getQueryParameter(key);
        return TextUtils.isEmpty(queryParameter) ? "" : queryParameter;
    }
}
