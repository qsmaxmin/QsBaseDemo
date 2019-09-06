package com.sugar.grapecollege.common.http;

import com.qsmaxmin.qsbase.common.aspect.Body;
import com.qsmaxmin.qsbase.common.aspect.POST;
import com.sugar.grapecollege.common.http.resp.ModelProductList;
import com.sugar.grapecollege.common.http.resp.ModelHomeHeader;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/18 17:37
 * @Description
 */

public interface HomeHttp {
    /**
     * 推荐位轮播图
     */
    @POST("/hehehe/dsfsdf") ModelHomeHeader requestHomeHeaderData(@Body BaseModelReq req);

    /**
     * 获取推荐字体列表
     */
    @POST("/hehehe/dsfsdf") ModelProductList requestRecommendFontListData(@Body BaseModelReq req);
}
