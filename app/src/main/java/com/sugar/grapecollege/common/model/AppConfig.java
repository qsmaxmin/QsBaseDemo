package com.sugar.grapecollege.common.model;


import com.qsmaxmin.qsbase.common.config.Property;
import com.qsmaxmin.qsbase.common.config.QsProperties;
import com.sugar.grapecollege.searcher.model.ModelSearch;

import java.util.List;
import java.util.Map;

/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description 应用配置管理类
 */
public class AppConfig extends QsProperties {
    /**
     * 单例模式
     */
    @Override public String initTag() {
        return "AppConfig";
    }


    private static AppConfig APP_CONFIG = new AppConfig("AppConfig");

    public static AppConfig getInstance() {
        return APP_CONFIG;
    }

    private AppConfig(String configName) {
        super(configName);
    }

    @Property public String                   testString;
    @Property public int                      testInt;
    @Property public short                    testShort;
    @Property public byte                     testByte;
    @Property public char                     testChar;
    @Property public long                     testLong;
    @Property public float                    testFloat;
    @Property public double                   testDouble;
    @Property public boolean                  testBoolean;
    @Property public ModelSearch              testObject;
    @Property public List<ModelSearch>        testObjectList;
    @Property public Map<String, ModelSearch> testObjectMap;

    @Property public Integer   testInteger;
    @Property public Short     testShorter;
    @Property public Byte      testByter;
    @Property public Character testCharcter;
    @Property public Long      testLonger;
    @Property public Float     testFloater;
    @Property public Double    testDoubler;
    @Property public Boolean   testBooleaner;

}
