package com.sugar.grapecollege.common.download.model;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/28 14:51
 * @Description 下载回调状态
 */

public enum CallbackState {
    CALLBACK_CONNECTING,
    CALLBACK_START,
    CALLBACK_RESTART,
    CALLBACK_WAIT,
    CALLBACK_CANCEL,
    CALLBACK_FAIL,
    CALLBACK_DOWNLOADING,
    CALLBACK_COMPLETE,
    CALLBACK_PAUSE
}
