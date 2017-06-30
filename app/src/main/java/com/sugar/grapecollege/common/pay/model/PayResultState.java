package com.sugar.grapecollege.common.pay.model;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/12 13:22
 * @Description 支付通道
 */
public enum PayResultState {

    SUCCESS("支付成功"), //
    CANCEL("取消"), //
    UN_KNOW("结果未知，请求服务端获取支付结果"), //
    FAIL("未知错误");//

    public final String message;

    PayResultState(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
