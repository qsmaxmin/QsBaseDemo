package com.sugar.grapecollege.common.http;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/3/28  2:07 PM
 * @Description
 */
public class HttpConstants {

    /**
     * 是否对请求体进行加密
     */
    public static final int STYLE_ENCRYPT_REQ = 0b01;

    /**
     * 是否对响应体进行解密
     */
    public static final int STYLE_DECRYPT_RESP = 0b01 << 1;


    public static boolean shouldEncryptRequest(int requestStyle) {
        return (requestStyle & STYLE_ENCRYPT_REQ) == STYLE_ENCRYPT_REQ;
    }

    public static boolean shouldDecryptResponse(int requestStyle) {
        return (requestStyle & STYLE_DECRYPT_RESP) == STYLE_DECRYPT_RESP;
    }
}
