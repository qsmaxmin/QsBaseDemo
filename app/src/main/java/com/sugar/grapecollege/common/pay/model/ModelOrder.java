package com.sugar.grapecollege.common.pay.model;

import com.qsmaxmin.qsbase.common.model.QsModel;
import com.sugar.grapecollege.common.model.BaseModel;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/12 9:15
 * @Description
 */

public class ModelOrder extends BaseModel {

    public ResponseDataModel responseData;

    public static class ResponseDataModel extends QsModel {
        public String fontId;

        /*支付宝支付返回字段*/
        public String orderString;

        /*以下是微信支付返回的字段*/
        public String appId;
        public String nonceStr;
        public String orderId;
        public String packageValue;
        public String partnerId;
        public String prepayId;
        public String sign;
        public int    timeStamp;

        @Override public String toString() {
            return "ResponseDataModel{" + "productId='" + fontId + '\'' + ", orderString='" + orderString + '\'' + ", appId='" + appId + '\'' + ", nonceStr='" + nonceStr + '\'' + ", orderId='" + orderId + '\'' + ", packageValue='" + packageValue + '\'' + ", partnerId='" + partnerId + '\'' + ", prepayId='" + prepayId + '\'' + ", sign='" + sign + '\'' + ", timeStamp=" + timeStamp + '}';
        }
    }

    @Override public String toString() {
        return "ModelOrder{" + "responseData=" + (responseData == null ? "null" : responseData.toString()) + '}';
    }
}
