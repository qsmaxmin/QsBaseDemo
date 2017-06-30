package com.sugar.grapecollege.common.utils;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 16:30
 * @Description
 */

public class UrlUtils {

    private static final String URL_ONLINE = "https://www.baidu.com/";

    private static final String URL_OFFLINE = "http://192.168.248.227:9555/";

    public static String getCurrentUrl() {
        return URL_ONLINE;
//        return QsHelper.getInstance().getApplication().isLogOpen() ? URL_OFFLINE : URL_ONLINE;
    }
}
