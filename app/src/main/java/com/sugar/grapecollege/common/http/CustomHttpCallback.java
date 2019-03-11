package com.sugar.grapecollege.common.http;

import com.qsmaxmin.qsbase.common.http.HttpBuilder;
import com.qsmaxmin.qsbase.common.http.HttpResponse;
import com.qsmaxmin.qsbase.common.http.QsHttpCallback;
import com.sugar.grapecollege.common.model.AppConstants;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/3/11 9:40
 * @Description
 */
public class CustomHttpCallback implements QsHttpCallback {

    @Override public void initHttpAdapter(HttpBuilder builder) throws Exception {
        //custom logic
        builder.setTerminal("http://www.baidu.com");
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("os", AppConstants.APP_OS);
        builder.addHeader("bundleId", AppConstants.PACKAGE_NAME);
    }

    @Override public void onHttpResponse(HttpResponse response) throws Exception {
        //custom logic
    }

    @Override public void onResult(HttpBuilder builder, Object result) throws Exception {
        //custom logic
    }
}
