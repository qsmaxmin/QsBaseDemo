package com.sugar.grapecollege.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qsmaxmin.qsbase.common.log.L;
import com.sugar.grapecollege.common.pay.PayHelper;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/5 13:00
 * @Description 微信支付回调
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.keySet() != null) {
            for (String key : extras.keySet()) {
                Object object = extras.get(key);
                L.i("WXPayEntryActivity", "intent data..... key:" + key + "   value:" + object);
            }
        }
        api = WXAPIFactory.createWXAPI(this, PayHelper.getInstance().getWeChatAppId());
        api.handleIntent(getIntent(), this);
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (api != null) api.handleIntent(intent, this);
    }

    @Override public void onReq(BaseReq baseReq) {
        L.i("WXPayEntryActivity", "applyWeChatPay onReq...... " + baseReq.openId);
    }

    @Override public void onResp(BaseResp resp) {
        finish();
        if (resp != null && resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX && resp instanceof PayResp) {
            PayResp payResp = (PayResp) resp;
            String prepayId = payResp.prepayId;
            L.i("WXPayEntryActivity", "applyWeChatPay onResp......  errorCode:" + resp.errCode + "  errorStr:" + resp.errStr + "   transaction:" + resp.transaction + "  prepareId:" + prepayId);
            PayHelper.getInstance().dispatchWeChatCallback(payResp);
        }
    }
}
