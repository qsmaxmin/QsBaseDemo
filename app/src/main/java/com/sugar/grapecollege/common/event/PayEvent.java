package com.sugar.grapecollege.common.event;

import com.sugar.grapecollege.common.pay.model.PayChannel;
import com.sugar.grapecollege.common.pay.model.PayResultState;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/5 11:27
 * @Description 支付事件
 */

public class PayEvent {
    /**
     * 支付结果通知
     */
    public static class PayResult {
        public PayChannel     channel;
        public PayResultState state;
        public String         productId;

        public PayResult(PayChannel channel, String productId, PayResultState state) {
            this.channel = channel;
            this.state = state;
            this.productId = productId;

        }

        @Override public String toString() {
            return "PayResult{" + "channel=" + channel + ", state=" + state + ", productId='" + productId + '\'' + '}';
        }
    }
}
