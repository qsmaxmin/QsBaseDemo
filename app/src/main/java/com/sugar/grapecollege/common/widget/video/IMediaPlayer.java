package com.sugar.grapecollege.common.widget.video;

/**
 * @CreateBy qsmaxmin
 * @Date 6/19/21  9:58 PM
 * @Description
 */
public interface IMediaPlayer {
    void start();

    void pause();

    int getDuration();

    int getCurrentPosition();

    void seekTo(int pos);

    boolean isPlaying();

    boolean setSpeed(float speed);

    float getSpeed();

    int getBufferPercentage();

    /**
     * 播放器是否已准备好，处于播放中或可播放状态
     */
    boolean isInPlaybackState();

    /**
     * 播放器发生错误时，需要手动重置播放器并重新加载视频
     */
    void reload();
}
