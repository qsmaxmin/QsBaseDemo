package com.sugar.grapecollege.common.http.resp;

import com.qsmaxmin.qsbase.common.model.QsModel;
import com.sugar.grapecollege.common.http.BaseModel;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/9/6 15:27
 * @Description
 */
public class ModelProductInfo extends BaseModel {

    public static class ProductInfo extends QsModel {
        public String productName;
    }
}
