package com.sugar.grapecollege.common.pay.model;

import com.qsmaxmin.qsbase.common.model.QsModel;
import com.sugar.grapecollege.common.model.BaseModelReq;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/10 9:28
 * @Description 生成订单请求的实体
 */

public class ModelGenerateOrderReq extends BaseModelReq {

    public SearchMapModel searchMap = new SearchMapModel();

    public static class SearchMapModel extends QsModel {
        public String productId;
        public int    channel;
    }
}
