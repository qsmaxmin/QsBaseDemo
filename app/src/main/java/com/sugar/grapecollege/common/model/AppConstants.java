package com.sugar.grapecollege.common.model;

import android.os.Build;
import android.os.Environment;

import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.utils.CommonUtils;
import com.sugar.grapecollege.common.utils.NetworkUtils;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 16:33
 * @Description 系统常量
 */

public class AppConstants {
    /**
     * 包名
     */
    public static final String PACKAGE_NAME     = QsHelper.getInstance().getApplication().getPackageName();
    /**
     * 客户端适用于系统 android
     */
    public static final String APP_OS           = "android";
    /**
     * 客户端 代号
     */
    public static final String APP_CODE         = "sugarFont";
    /**
     * 客户端 版本号
     */
    public static final String CLIENT_VERSION   = CommonUtils.getAppVersion();
    /**
     * 设备唯一标示
     */
    public static final String IMEI             = NetworkUtils.getDeviceIMEI();
    /**
     * 设备系统版本号
     */
    public static final String DEVICE_VERSION   = Build.VERSION.RELEASE;
    /**
     * 手机厂商
     */
    public static final String DEVICE_FACTORY   = Build.MANUFACTURER;
    /**
     * 手机型号
     */
    public static final String DEVICE_MODEL     = Build.MODEL;
    /**
     * App在SD卡中的父目录
     */
    public static final String ROOT_SDCARD_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + QsHelper.getInstance().getApplication().getString(R.string.app_name);
    /**
     * 统一下载路径(App升级，应用下载的root工具等)
     */
    public static final String PATH_DOWNLOAD    = ROOT_SDCARD_PATH + "/download";
    /**
     * 图片缓存目录大小
     */
    public static final int    IMG_CACHE_SIZE   = 300 * 1024 * 1024;
}
