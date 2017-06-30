package com.sugar.grapecollege.common.http;

import com.qsmaxmin.qsbase.common.aspect.Body;
import com.qsmaxmin.qsbase.common.aspect.POST;
import com.sugar.grapecollege.common.model.BaseModelReq;
import com.sugar.grapecollege.common.pay.model.ModelOrder;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/5 14:32
 * @Description 支付相关
 */

public interface PayHttp {
    /**
     * 生成订单
     */
    @POST("/store/sugar/addorder") ModelOrder generateOrder(@Body BaseModelReq req);
}
