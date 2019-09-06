package com.sugar.grapecollege.test.model;

import com.sugar.grapecollege.common.http.BaseModel;

import java.util.List;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/4 16:40
 * @Description
 */

public class TestModel extends BaseModel {
    public List<TestModelInfo> list;


    public static class TestModelInfo {
        public String testName;
    }
}
