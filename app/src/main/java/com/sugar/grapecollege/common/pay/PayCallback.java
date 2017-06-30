package com.sugar.grapecollege.common.pay;

import com.sugar.grapecollege.common.pay.model.PayChannel;
import com.sugar.grapecollege.common.pay.model.PayResultState;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/6 9:00
 * @Description
 */

public interface PayCallback {
    void onPayResult(PayChannel channel, String fontId, PayResultState state);
}
