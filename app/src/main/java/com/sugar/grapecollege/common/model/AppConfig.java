package com.sugar.grapecollege.common.model;


import com.qsmaxmin.qsbase.common.config.Property;
import com.qsmaxmin.qsbase.common.config.QsProperties;

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

    @Property public String    testString;
    @Property public int       testInt;
    @Property public short     testShort;
    @Property public byte      testByte;
    @Property public char      testChar;
    @Property public long      testLong;
    @Property public float     testFloat;
    @Property public double    testDouble;
    @Property public boolean   testBoolean;
    @Property public Short     testShort_;
    @Property public Integer   testInteger_;
    @Property public Byte      testByte_;
    @Property public Character testChar_;
    @Property public Long      testLong_;
    @Property public Float     testFloat_;
    @Property public Double    testDouble_;
    @Property public Boolean   testBoolean_;
    @Property public TestModel testModel;

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
                '}';
    }
}
