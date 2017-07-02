package com.sugar.grapecollege.searcher.model;

import com.sugar.grapecollege.common.model.BaseModel;

import java.util.ArrayList;

/**
 * @CreateBy qsmaxmin
 * @Date 17/7/2  下午9:40
 * @Description
 */

public class ModelSearch extends BaseModel {

    ArrayList<ModelProduct> responseData;

    public static class ModelProduct {
        public String name;
    }
}
