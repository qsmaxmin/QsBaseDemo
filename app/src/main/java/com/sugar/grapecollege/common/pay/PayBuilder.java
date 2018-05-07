package com.sugar.grapecollege.common.pay;

import android.app.Activity;
import android.os.Looper;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.common.event.PayEvent;
import com.sugar.grapecollege.common.http.PayHttp;
import com.sugar.grapecollege.common.model.UserConfig;
import com.sugar.grapecollege.common.pay.model.AliPayResult;
import com.sugar.grapecollege.common.pay.model.ModelGenerateOrderReq;
import com.sugar.grapecollege.common.pay.model.ModelOrder;
import com.sugar.grapecollege.common.pay.model.PayChannel;
import com.sugar.grapecollege.common.pay.model.PayResultState;
import com.sugar.grapecollege.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/6 9:04
 * @Description
 */

public class PayBuilder {

    private final Activity    activity;
    private       String      weChatPayAppId;
    private final PayCallback callback;
    private final PayChannel  payChannel;
    private final String      fontId;
    private       String      prepayId;

    PayBuilder(Activity activity, String fontId, PayChannel channel, String weChatPayAppId, PayCallback callback) {
        this.activity = activity;
        this.fontId = fontId;
        this.payChannel = channel;
        this.weChatPayAppId = weChatPayAppId;
        this.callback = callback;
    }

    public void requestPay() {
        if (activity != null && !TextUtils.isEmpty(fontId) && payChannel != null) {
            if (isChildThread()) {
                requestOrder();
            } else {
                QsHelper.getInstance().getThreadHelper().getHttpThreadPoll().execute(new Runnable() {
                    @Override public void run() {
                        requestOrder();
                    }
                });
            }
        } else {
            postCallbackEvent(PayResultState.FAIL);
        }
    }

    private void requestOrder() {
        if (UserConfig.getInstance().isLogin()) {
            PayHttp http = QsHelper.getInstance().getHttpHelper().create(PayHttp.class, System.currentTimeMillis());
            ModelGenerateOrderReq modelReq = new ModelGenerateOrderReq();
            modelReq.searchMap.productId = fontId;
            modelReq.searchMap.channel = payChannel.index;
            ModelOrder order = null;
            try {
                order = http.generateOrder(modelReq);
            } catch (Exception e) {
                postCallbackEvent(PayResultState.FAIL);
                L.e("PayBuilder", "requestOrder : " + e.getMessage());
            }
            if (order != null && "0".equals(order.code)) {
                switch (payChannel) {
                    case ALIPAY:
                        applyAliPay(order);
                        break;
                    case WECHATPAY:
                        applyWeChatPay(order);
                        break;
                    case UNIONPAY:
//                        J2WToast.show("not support union pay!");
                        break;
                }
            } else {
                postCallbackEvent(PayResultState.FAIL);
                L.e("PayBuilder", "PayHelper request order info failed....");
            }
        } else {
            postCallbackEvent(PayResultState.FAIL);
            L.e("PayBuilder", "requestPay  never login !!");
        }
    }

    /**
     * 支付宝支付
     */
    private void applyAliPay(final ModelOrder orderInfo) {
        if (activity != null && !TextUtils.isEmpty(fontId) && orderInfo != null && orderInfo.responseData != null && !TextUtils.isEmpty(orderInfo.responseData.orderString)) {
            L.i("PayBuilder", "applyAliPay  请求支付  orderInfo=" + orderInfo.toString());
            PayTask aliPay = new PayTask(activity);
            Map<String, String> result = aliPay.payV2(orderInfo.responseData.orderString, true);

            AliPayResult aliPayResult = new AliPayResult(result);
            L.i("PayBuilder", "applyAliPay  支付结束  result=" + aliPayResult.toString());
            dispatchAliPayCallback(fontId, aliPayResult);
        } else {
            postCallbackEvent(PayResultState.FAIL);
        }
    }

    /**
     * 微信支付
     * 支付回调见{@link WXPayEntryActivity}
     */
    private void applyWeChatPay(ModelOrder order) {
        if (order != null && order.responseData != null) {
            this.weChatPayAppId = TextUtils.isEmpty(order.responseData.appId) ? weChatPayAppId : order.responseData.appId;
            this.prepayId = order.responseData.prepayId;
            L.e("PayBuilder", "applyWeChatPay  order:" + order.toString());

            IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, null);
            msgApi.registerApp(weChatPayAppId);

            PayReq request = new PayReq();
            request.appId = order.responseData.appId;
            request.partnerId = order.responseData.partnerId;
            request.prepayId = order.responseData.prepayId;
            request.packageValue = order.responseData.packageValue;
            request.nonceStr = order.responseData.nonceStr;
            request.timeStamp = String.valueOf(order.responseData.timeStamp);
            request.sign = order.responseData.sign;
            msgApi.sendReq(request);
        } else {
            postCallbackEvent(PayResultState.FAIL);
        }
    }

    /**
     * aliPay支付回调
     */
    private void dispatchAliPayCallback(String fontId, AliPayResult aliPayResult) {
        if (aliPayResult != null && aliPayResult.getResultStatus() != null) {
            PayResultState payResultState;
            String resultStatus = aliPayResult.getResultStatus();
            L.i("PayBuilder", "dispatchAliPayCallback...... resultStatus=" + resultStatus);
            switch (resultStatus) {
                case "9000"://成功
                    payResultState = PayResultState.SUCCESS;
                    break;
                case "6004"://结果未知
                    payResultState = PayResultState.UN_KNOW;
                    break;
                case "6001"://支付取消
                    payResultState = PayResultState.CANCEL;
                    break;
                case "4000"://支付失败
                default:
                    payResultState = PayResultState.FAIL;
                    break;
            }
            postCallbackEvent(payResultState);
        }
    }

    private boolean isChildThread() {
        return Thread.currentThread() != Looper.getMainLooper().getThread();
    }

    private void postCallbackEvent(final PayResultState resultState) {
        if (isChildThread()) {
            QsHelper.getInstance().getThreadHelper().getMainThread().execute(new Runnable() {
                @Override public void run() {
                    if (callback != null) callback.onPayResult(payChannel, fontId, resultState);
                }
            });
        } else {
            if (callback != null) callback.onPayResult(payChannel, fontId, resultState);
        }
        QsHelper.getInstance().eventPost(new PayEvent.PayResult(payChannel, fontId, resultState));
        PayHelper.getInstance().removeBuilder(this);
    }

    void onWeChatCallback(PayResultState payResultState, PayResp resp) {
        if (resp != null && !TextUtils.isEmpty(resp.prepayId) && resp.prepayId.equals(this.prepayId)) {
            postCallbackEvent(payResultState);
        }
    }
}
