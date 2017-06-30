package com.sugar.grapecollege.common.model;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 16:25
 * @Description http 响应体基类
 * todo 因为框架中对code字段做了处理，所以将code字段提到了J2WBaseModel里，后期需要对字段进行统一
 */

public class BaseModel {
    public int    code;
    public String msg;

    public boolean isLastPage;
}
