package com.sugar.grapecollege.home.model;

import com.qsmaxmin.qsbase.common.model.QsModel;
import com.sugar.grapecollege.common.model.BaseModel;

import java.util.List;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/18 17:38
 * @Description
 */

public class ModelHomeHeader extends BaseModel {

    public List<ResponseDataModel> responseData;

    public List<ResponseDataModel> data;

    public static class ResponseDataModel extends QsModel {
        public String picUrl;
        public String bannerType;
        public String bannerValue;
        public String bannerName;
    }
}
