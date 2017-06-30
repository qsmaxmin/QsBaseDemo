package com.sugar.grapecollege.common.download.threadpoll;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/3/21 20:11
 * @Description 线程池管理类
 */

public class DownloadThreadPollManager {

    private CustomThreadPoll downloadThreadPoll;
    private CustomThreadPoll workThreadPoll = new CustomThreadPoll(10);

    private static final DownloadThreadPollManager instance = new DownloadThreadPollManager();

    private DownloadThreadPollManager() {
    }

    public static DownloadThreadPollManager getInstance() {
        return instance;
    }

    public void init(int threadCount) {
        if (downloadThreadPoll == null) {
            synchronized (this) {
                if (downloadThreadPoll == null) {
                    downloadThreadPoll = new CustomThreadPoll(threadCount);
                }
            }
        }
    }

    public CustomThreadPoll getDownloadService() {
        init(5);
        return downloadThreadPoll;
    }

    public CustomThreadPoll getWorkService() {
        return workThreadPoll;
    }
}
