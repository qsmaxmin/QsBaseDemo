package com.sugar.grapecollege.searcher.model;

import com.qsmaxmin.qsbase.common.model.QsModel;
import com.sugar.grapecollege.common.model.BaseModelReq;

/**
 * @CreateBy qsmaxmin
 * @Date 17/5/9 15:56
 * @Description
 */
public class ModelSearchReq extends BaseModelReq {

    public RequestBody searchMap = new RequestBody();

    public class RequestBody extends QsModel {
        public String searchStr;
    }
}
