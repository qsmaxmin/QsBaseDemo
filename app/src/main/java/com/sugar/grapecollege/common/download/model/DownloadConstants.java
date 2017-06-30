package com.sugar.grapecollege.common.download.model;

import android.os.Environment;

import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 17:44
 * @Description
 */

public class DownloadConstants {
    /**
     * 最大下载线程数
     */
    public static final int MAX_DOWNLOAD_COUNT = 3;

    /**
     * sdcard根目录
     */
    public static final String ROOT_SDCARD_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + QsHelper.getInstance().getApplication().getString(R.string.app_name);

    /**
     * 压缩包解压路径
     */
    public static final String DEFAULT_TTF_PATH = ROOT_SDCARD_PATH + "/ttf";
    /**
     * 压缩包下载路径
     */
    public static final String DEFAULT_ZIP_PATH = ROOT_SDCARD_PATH + "/zip";
}
