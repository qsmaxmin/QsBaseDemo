package com.sugar.grapecollege.common.http;

import android.os.Build;
import android.text.TextUtils;

import com.qsmaxmin.qsbase.common.http.Chain;
import com.qsmaxmin.qsbase.common.http.HttpInterceptor;
import com.qsmaxmin.qsbase.common.http.HttpRequest;

import java.util.HashMap;

import androidx.annotation.NonNull;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/7/28 16:45
 * @Description
 */
public class CustomHttpInterceptor implements HttpInterceptor {

    @NonNull @Override public Response onIntercept(@NonNull Chain chain) throws Exception {
        HttpRequest request = chain.getRequest();

        //添加自定义公共参数
        String requestType = request.getRequestType();
        switch (requestType) {
            case "POST":
                HashMap<String, Object> formMap = request.getFiledMap();
                addCommonParamsForPost(formMap);
                break;
            case "GET":
                HashMap<String, String> queryMap = request.getQueryMap();
                addCommonParamsForGet(queryMap);
                break;
        }

        //该值来源于@RequestStype注解标记
        int requestStyle = request.getRequestStyle();
        if (HttpConstants.shouldEncryptRequest(requestStyle)) {
            HashMap<String, Object> filedMap = request.getFiledMap();
            //对请求参数进行加密......
        }

        //添加公共主机地址
        if (TextUtils.isEmpty(request.getTerminal())) {
            request.setTerminal("https://www.baidu.com");
        }


        //添加自定义公共请求头
        request.addHeader("key", "value");

        //执行http请求
        if (HttpConstants.shouldDecryptResponse(requestStyle)) {
            Response response = chain.process();
            ResponseBody body = response.body();
            if (body == null) throw new Exception("response body is null");
            byte[] bytes = body.bytes();
            body.close();
            //此处执行解密操作.......
            ResponseBody newBody = ResponseBody.create(body.contentType(), bytes);
            return response.newBuilder().body(newBody).build();
        } else {
            return chain.process();
        }
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
