package com.sugar.grapecollege.common.model;


import com.qsmaxmin.annotation.properties.AutoProperty;
import com.qsmaxmin.annotation.properties.Property;
import com.qsmaxmin.qsbase.plugin.property.QsProperties;

import java.util.HashMap;

/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description 应用配置存储类-可存万物
 */
@AutoProperty
public class AppConfig extends QsProperties {
    private static AppConfig APP_CONFIG = new AppConfig("AppConfig");

    /**
     * 单例模式
     */
    public static AppConfig getInstance() {
        return APP_CONFIG;
    }

    /**
     * @param configName 数据唯一key
     */
    private AppConfig(String configName) {
        super(configName);
    }

    @Property public String testString;
    @Property public int    testInt;
    @Property public short                   testShort;
    @Property public byte                    testByte;
    @Property public char                    testChar;
    @Property public long                    testLong;
    @Property public float                   testFloat;
    @Property public double                  testDouble;
    @Property public boolean                 testBoolean;
    @Property public Short                   testShort_;
    @Property public Integer                 testInteger_;
    @Property public Byte                    testByte_;
    @Property public Character               testChar_;
    @Property public Long                    testLong_;
    @Property public Float                   testFloat_;
    @Property public Double                  testDouble_;
    @Property public Boolean                 testBoolean_;
    @Property public TestModel               testModel;
    @Property public HashMap<String, String> testMap;

    @Override public String toString() {
        return "AppConfig{" +
                "\ntestString='" + testString + '\'' +
                ", \ntestInt=" + testInt +
                ", \ntestShort=" + testShort +
                ", \ntestByte=" + testByte +
                ", \ntestChar=" + testChar +
                ", \ntestLong=" + testLong +
                ", \ntestFloat=" + testFloat +
                ", \ntestDouble=" + testDouble +
                ", \ntestBoolean=" + testBoolean +
                ", \ntestShort_=" + testShort_ +
                ", \ntestInteger_=" + testInteger_ +
                ", \ntestByte_=" + testByte_ +
                ", \ntestChar_=" + testChar_ +
                ", \ntestLong_=" + testLong_ +
                ", \ntestFloat_=" + testFloat_ +
                ", \ntestDouble_=" + testDouble_ +
                ", \ntestBoolean_=" + testBoolean_ +
                ", \ntestModel=" + testModel +
                ", \ntestMap=" + (testMap == null ? "null" : testMap.toString()) +
                '}';
    }
}
