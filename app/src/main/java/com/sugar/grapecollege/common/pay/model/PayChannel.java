package com.sugar.grapecollege.common.pay.model;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/12 13:22
 * @Description 支付通道
 */
public enum PayChannel {

    WECHATPAY(1), UNIONPAY(2), ALIPAY(3);

    public final int index;

    PayChannel(int index) {
        this.index = index;
    }
}
