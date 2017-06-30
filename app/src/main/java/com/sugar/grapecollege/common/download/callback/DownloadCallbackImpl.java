package com.sugar.grapecollege.common.download.callback;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/2 17:29
 * @Description 接口实现类，可以被继承并重写需要的方法
 */

public class DownloadCallbackImpl implements DownloadCallback {
    @Override public void onDownloadConnecting(String fontId) {

    }

    @Override public void onDownloadStart(String fontId) {

    }

    @Override public void onDownloadPause(String fontId) {

    }

    @Override public void onDownloadRestart(String fontId, int percent) {

    }

    @Override public void onDownloading(String fontId, int progress) {

    }

    @Override public void onDownloadComplete(String fontId) {

    }

    @Override public void onDownloadFailed(String fontId, String message) {

    }

    @Override public void onDownloadWaite(String fontId) {

    }

    @Override public void onDownloadCancel(String fontId) {

    }
}
