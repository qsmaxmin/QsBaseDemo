package com.sugar.grapecollege.web.model;

/**
 * @CreateBy qsmaxmin
 * @Date 16/8/24 下午4:42
 * @Description 网页视图常量
 */
public class WebConstants {

    /**
     * 属性动画
     */
    public static final String ANIM_TRANSLATION_X       = "translationX";
    public static final String ANIM_TRANSLATION_Y       = "translationY";
    public static final String ANIM_ALPHA               = "alpha";
    public static final String ANIM_ROTATION            = "rotation";
    public static final String ANIM_ROTATION_X          = "rotationX";
    public static final String ANIM_ROTATION_Y          = "rotationY";
    public static final String ANIM_SCALE_X             = "scaleX";
    public static final String ANIM_SCALE_Y             = "scaleY";
    /**
     * 请求标志
     */
    public static final String REQUEST_CODE             = "request_code";
    /**
     * 状态 - key
     */
    public static final String BUNDLE_STATE             = "bundle_state";
    public static final int    STATE_URL                = 1;
    public static final int    STATE_DATA               = 2;
    /**
     * url
     */
    public static final String BUNDLE_URL_KEY           = "bundle_url_key";
    /**
     * 静态jsp数据
     * {@link android.webkit.WebView#loadDataWithBaseURL(String, String, String, String, String)}
     * {@link android.webkit.WebView#loadData(String, String, String)}
     */
    public static final String BUNDLE_DATA_KEY          = "bundle_data_key";
    /**
     * 标题
     */
    public static final String BUNDLE_TITLE_KEY         = "bundle_title_key";
    /**
     * 分享-url
     */
    public static final String BUNDLE_SHARE_URL_KEY     = "bundle_share_url_key";
    /**
     * 分享-标题
     */
    public static final String BUNDLE_SHARE_TITLE_KEY   = "bundle_share_title_key";
    /**
     * 分享-内容
     */
    public static final String BUNDLE_SHARE_CONTENT_KEY = "bundle_share_content_key";
    /**
     * 分享-图标URL
     */
    public static final String BUNDLE_SHARE_ICON_KEY    = "bundle_share_icon_key";


    public static final String FOUNDER_SCHEME = "founder";

    public static final String PATH_COLSE_WEBVIEW = "/closewebview";
}
