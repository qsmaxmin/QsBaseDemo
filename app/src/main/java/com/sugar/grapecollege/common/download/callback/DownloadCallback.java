package com.sugar.grapecollege.common.download.callback;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 17:01
 * @Description 下载回调
 */

public interface DownloadCallback {

    /**
     * 网络连接ing
     */
    void onDownloadConnecting(String fontId);

    /**
     * 开始下载，更新progressbar
     */
    void onDownloadStart(String fontId);

    /**
     * 暂停下载
     */
    void onDownloadPause(String fontId);

    /**
     * 继续下载
     */
    void onDownloadRestart(String fontId, int percent);

    /**
     * 正在下载，更新progressbar
     */
    void onDownloading(String fontId, int progress);

    /**
     * 结束下载，更新progressbar
     */
    void onDownloadComplete(String fontId);

    /**
     * 下载失败
     */
    void onDownloadFailed(String fontId, String message);

    /**
     * 加入等待中
     */
    void onDownloadWaite(String fontId);

    /**
     * 下载取消
     */
    void onDownloadCancel(String fontId);
}
