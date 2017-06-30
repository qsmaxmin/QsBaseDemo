package com.sugar.grapecollege.common.greendao.model;

import java.io.Serializable;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 11:42
 * @Description 下载状态, 序列化到数据库
 */
public enum DownloadState implements Serializable {
    DOWNLOAD_INIT,
    DOWNLOAD_ING,
    DOWNLOAD_COMPLETE,
    DOWNLOAD_PAUSE,
    DOWNLOAD_WAIT
}
