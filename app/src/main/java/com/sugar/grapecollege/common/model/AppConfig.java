//package com.sugar.grapecollege.common.model;
//
//import j2w.team.modules.appconfig.J2WProperties;
//import j2w.team.modules.appconfig.Property;
//
///**
// * @CreateBy qsmaxmin
// * @Date 16/7/29
// * @Description 应用配置管理类
// */
//public class AppConfig extends J2WProperties {
//    /**
//     * 单例模式
//     */
//    @Override public String initTag() {
//        return "AppConfig";
//    }
//
//    @Override public int initType() {
//        return J2WProperties.OPEN_TYPE_DATA;
//    }
//
//    private static AppConfig APP_CONFIG = new AppConfig("AppConfig");
//
//    public static AppConfig getInstance() {
//        return APP_CONFIG;
//    }
//
//    private AppConfig() {
//    }
//
//    private AppConfig(String configName) {
//        super(configName);
//    }
//
//    @Property public String  userId;          //强制下载，忽略非wifi状态
//    @Property public boolean forceDownload;          //强制下载，忽略非wifi状态
//}
