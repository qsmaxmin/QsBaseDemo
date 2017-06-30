package com.sugar.grapecollege.web.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.qsmaxmin.qsbase.common.aspect.ThreadPoint;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.common.presenter.GrapeCollegePresenter;
import com.sugar.grapecollege.web.fragment.WebFragment;
import com.sugar.grapecollege.web.model.WebConstants;

/**
 * @CreateBy qsmaxmin
 * @Date 16/8/24 下午4:42
 * @Description 网页视图
 */
public class WebPresenter extends GrapeCollegePresenter<WebFragment> {

    /**
     * 读取数据
     */
    @ThreadPoint public void readData(Bundle bundle) {
        if (bundle == null) {
            getView().activityFinish();
            return;
        }

        String url = bundle.getString(WebConstants.BUNDLE_URL_KEY);
        String title = bundle.getString(WebConstants.BUNDLE_TITLE_KEY);
        String data = bundle.getString(WebConstants.BUNDLE_DATA_KEY);
        int state = bundle.getInt(WebConstants.BUNDLE_STATE, -1);
        if (state > 0) {
            switch (state) {
                default:
                case WebConstants.STATE_URL:
                    getView().showWeb(title, url, state);
                    break;
                case WebConstants.STATE_DATA:
                    getView().showWeb(title, data, state);
                    break;
            }
        } else {
            if (!TextUtils.isEmpty(url)) {
                getView().showWeb(title, url, WebConstants.STATE_URL);
            } else if (!TextUtils.isEmpty(data)) {
                getView().showWeb(title, data, WebConstants.STATE_DATA);
            }
        }
    }

    /**
     * 后期扩展
     */
    public boolean shouldOverrideUrl(WebView view, String url) {
        if (TextUtils.isEmpty(url)) return false;
        L.e(getTag(), url + "");
        Uri uri = Uri.parse(url);
        String scheme = uri.getScheme();
        if ("https".equals(scheme) || "http".equals(scheme) || "ftp".equals(scheme)) {
            return false;
        } else if (WebConstants.FOUNDER_SCHEME.equals(scheme)) {
            return parseFounderScheme(uri);
        } else {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                QsHelper.getInstance().getScreenHelper().currentActivity().startActivity(intent);
                return true;
            } catch (Exception e) {
                L.e(getTag(), e.getMessage());
            }
            return false;
        }
    }

    private boolean parseFounderScheme(Uri uri) {
        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) return false;
        switch (path) {
            case WebConstants.PATH_COLSE_WEBVIEW:
                String toast = uri.getQueryParameter("message");
                String code = uri.getQueryParameter("code");
                if (!TextUtils.isEmpty(toast)) {
//                    J2WToast.show(toast);
                }
                getView().activityFinish();
                return true;
            default:
                return false;
        }
    }
}
