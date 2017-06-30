package com.sugar.grapecollege.common.http;

import com.qsmaxmin.qsbase.common.aspect.Body;
import com.qsmaxmin.qsbase.common.aspect.POST;
import com.sugar.grapecollege.common.model.BaseModelReq;
import com.sugar.grapecollege.product.model.ModelProductList;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/9 15:53
 * @Description
 */

public interface SearcherHttp {

    /**
     * 获取搜索数据
     * store/sugar/search
     */
    @POST("/www/hehheh") ModelProductList requestSearchData(@Body BaseModelReq req);

}
