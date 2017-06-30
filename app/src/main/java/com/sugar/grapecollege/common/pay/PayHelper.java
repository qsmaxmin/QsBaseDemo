package com.sugar.grapecollege.common.pay;

import android.app.Activity;

import com.sugar.grapecollege.common.pay.model.PayChannel;
import com.sugar.grapecollege.common.pay.model.PayResultState;
import com.sugar.grapecollege.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;

import java.util.ArrayList;
import java.util.List;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/5 13:22
 * @Description 支付处理类
 * 回调统一使用eventBus{@link com.sugar.grapecollege.common.event.PayEvent.PayResult}
 */

public class PayHelper {

    /**
     * 微信支付appId
     */
    private       String           weChatPayAppId = "aaaaaaaaaaaaaaaaaaa";
    private final List<PayBuilder> builderList    = new ArrayList<>();

    private static PayHelper helper;

    public static PayHelper getInstance() {
        if (helper == null) {
            synchronized (PayHelper.class) {
                if (helper == null) helper = new PayHelper();
            }
        }
        return helper;
    }

    public PayBuilder createPayBuilder(Activity activity, String productId, PayChannel channel) {
        return createPayBuilder(activity, productId, channel, null);
    }

    public PayBuilder createPayBuilder(Activity activity, String productId, PayChannel channel, PayCallback callback) {
        PayBuilder payBuilder = new PayBuilder(activity, productId, channel, weChatPayAppId, callback);
        builderList.add(payBuilder);
        return payBuilder;
    }


    /**
     * 微信支付回调，微信支付有点另类，所以特殊处理
     * 由{@link WXPayEntryActivity}发起
     */
    public void dispatchWeChatCallback(PayResp resp) {
        synchronized (builderList) {
            for (PayBuilder builder : builderList) {
                if (builder != null && resp != null) {
                    PayResultState payResultState;
                    switch (resp.errCode) {
                        case BaseResp.ErrCode.ERR_OK:
                            payResultState = PayResultState.SUCCESS;
                            break;
                        case BaseResp.ErrCode.ERR_USER_CANCEL:
                            payResultState = PayResultState.CANCEL;
                            break;
                        default:
                            payResultState = PayResultState.FAIL;
                            break;
                    }
                    builder.onWeChatCallback(payResultState, resp);
                }
            }
        }
    }

    void removeBuilder(PayBuilder payBuilder) {
        if (payBuilder != null && builderList.contains(payBuilder)) {
            synchronized (builderList) {
                if (builderList.contains(payBuilder)) {
                    builderList.remove(payBuilder);
                }
            }
        }
    }

    public String getWeChatAppId() {
        return weChatPayAppId;
    }

}
