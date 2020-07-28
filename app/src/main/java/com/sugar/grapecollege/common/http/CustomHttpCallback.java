package com.sugar.grapecollege.common.http;

import android.os.Build;
import android.text.TextUtils;

import com.qsmaxmin.qsbase.common.http.HttpRequest;
import com.qsmaxmin.qsbase.common.http.HttpResponse;
import com.qsmaxmin.qsbase.common.http.QsHttpCallback;

import java.util.HashMap;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/7/28 16:45
 * @Description
 */
public class CustomHttpCallback implements QsHttpCallback {
    @Override public void processParams(HttpRequest request) throws Exception {
        if (TextUtils.isEmpty(request.getTerminal())) {
            request.setTerminal("https://www.baidu.com");
        }

        String requestType = request.getRequestType();
        switch (requestType) {
            case "POST":
                HashMap<String, Object> formMap = request.getFormMap();
                addCommonParamsForPost(formMap);
                break;
            case "GET":
                HashMap<String, String> queryMap = request.getQueryMap();
                addCommonParamsForGet(queryMap);
                break;
        }

        request.addHeader("key", "value");
        //....
    }

    @Override public void onHttpResponse(HttpRequest request, HttpResponse response) throws Exception {

    }

    @Override public void onHttpComplete(HttpRequest builder, Object result) throws Exception {

    }

    private void addCommonParamsForGet(HashMap<String, String> queryMap) {
        queryMap.put("userId", "123456");
        queryMap.put("sys", String.valueOf(Build.VERSION.SDK_INT));
    }

    private void addCommonParamsForPost(HashMap<String, Object> formMap) {
        formMap.put("userId", "123456");
        formMap.put("sys", String.valueOf(Build.VERSION.SDK_INT));
    }
}
