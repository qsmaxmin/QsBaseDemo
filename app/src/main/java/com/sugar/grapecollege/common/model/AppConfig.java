package com.sugar.grapecollege.common.model;


import com.qsmaxmin.annotation.properties.AutoProperty;
import com.qsmaxmin.annotation.properties.Property;
import com.qsmaxmin.qsbase.plugin.property.QsProperties;

import java.util.Set;

import androidx.annotation.NonNull;

/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description 应用配置存储类-可存万物
 */
@AutoProperty
public class AppConfig extends QsProperties {
    private static final AppConfig APP_CONFIG = new AppConfig("AppConfig");

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

    @Property public String      testString;
    @Property public int         testInt;
    @Property public long        testLong;
    @Property public float       testFloat;
    @Property public boolean     testBoolean;
    @Property public Integer     testInteger_;
    @Property public Long        testLong_;
    @Property public Float       testFloat_;
    @Property public Boolean     testBoolean_;
    @Property public Set<String> testSet;

    @Override @NonNull public String toString() {
        return "AppConfig{" +
                "\ntestString='" + testString + '\'' +
                ", \ntestInt=" + testInt +
                ", \ntestLong=" + testLong +
                ", \ntestFloat=" + testFloat +
                ", \ntestBoolean=" + testBoolean +
                ", \ntestInteger_=" + testInteger_ +
                ", \ntestLong_=" + testLong_ +
                ", \ntestFloat_=" + testFloat_ +
                ", \ntestBoolean_=" + testBoolean_ +
                ", \ntestSet=" + (testSet == null ? "null" : testSet.toString()) +
                '}';
    }
}
