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

    @Override public int initType() {
        return QsProperties.OPEN_TYPE_DATA;
    }

    private static AppConfig APP_CONFIG = new AppConfig("AppConfig");

    public static AppConfig getInstance() {
        return APP_CONFIG;
    }

    private AppConfig() {
    }

    private AppConfig(String configName) {
        super(configName);
    }

    @Property public String  userId;
    @Property public boolean forceDownload;
}
