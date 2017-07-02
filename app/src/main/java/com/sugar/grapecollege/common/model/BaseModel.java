package com.sugar.grapecollege.common.model;


import com.qsmaxmin.qsbase.common.model.QsModel;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 16:25
 * @Description http 响应体基类
 */

public class BaseModel extends QsModel {
    public int    code;
    public String msg;

    public boolean isLastPage;
}
