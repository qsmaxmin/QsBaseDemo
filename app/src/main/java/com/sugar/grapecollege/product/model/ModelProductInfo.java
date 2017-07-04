package com.sugar.grapecollege.product.model;

import com.qsmaxmin.qsbase.common.model.QsModel;
import com.sugar.grapecollege.common.model.BaseModel;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/8 16:28
 * @Description
 */

public class ModelProductInfo extends BaseModel{
    public ProductInfo resp;

    public static class ProductInfo extends QsModel {
        public String productId;
        public String productName;
    }
}
