package com.sugar.grapecollege.common.widget.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.qsmaxmin.qsbase.common.log.L;

/**
 * @CreateBy qsmaxmin
 * @Date 6/19/21  6:53 PM
 * @Description
 */
public abstract class AbsMediaController {
    static final  int             ERROR_CODE_INIT = 996;
    private final ProgressUpdater progressUpdater;
    private       View            mContentView;
    private       IMediaPlayer    mPlayer;

    public AbsMediaController() {
        progressUpdater = new ProgressUpdater();
    }

    private class ProgressUpdater implements Runnable {
        private boolean running;

        @Override public void run() {
            if (running && mPlayer.isPlaying()) {
                onMediaPlayerPositionUpdate(mPlayer.getCurrentPosition(), mPlayer.getDuration());
                postDelayed(this, getPlayerUpdateDelayed());
            }
        }

        private void start() {
            running = true;
            removeCallback(this);
            post(this);
        }

        private void cancel() {
            running = false;
            removeCallback(this);
        }
    }

    protected long getPlayerUpdateDelayed() {
        return 1000;
    }

    final void setMediaPlayer(IMediaPlayer player) {
        this.mPlayer = player;
    }

    public final IMediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    final void setAnchorView(ViewGroup anchorView) {
        this.mContentView = onCreateContentView(LayoutInflater.from(anchorView.getContext()), anchorView);
    }

    public final View getContentView() {
        return mContentView;
    }

    public final Context getContext() {
        return mContentView.getContext();
    }

    public final void post(Runnable action) {
        mContentView.post(action);
    }

    public final void postDelayed(Runnable action, long delayed) {
        if (delayed <= 0) {
            post(action);
        } else {
            mContentView.postDelayed(action, delayed);
        }
    }

    public final void removeCallback(Runnable action) {
        mContentView.removeCallbacks(action);
    }

    protected boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    protected boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    protected abstract View onCreateContentView(LayoutInflater inflater, ViewGroup parent);

    protected abstract void onPlayerLoading();

    protected abstract void onPlayerPrepared();

    protected abstract void onPlayerStarted();

    protected abstract void onPlayerRenderingStart();

    protected abstract void onPlayerPositionUpdate(int position, int duration);

    protected abstract void onPlayerBufferingStart();

    protected abstract void onPlayerBufferingEnd();

    protected abstract void onPlayerPaused();

    protected abstract void onPlayerCompletion();

    protected abstract void onPlayerStopped();

    protected abstract void onPlayerError();

    final void onMediaPlayerLoading() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerLoading.........");
        onPlayerLoading();
    }

    final void onMediaPlayerPrepared() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerPrepared.........");
        progressUpdater.start();
        onPlayerPrepared();
    }

    final void onMediaPlayerStarted() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerStarted.........");
        onPlayerStarted();
    }

    final void onMediaPlayerRenderingStart() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerRenderingStart.........");
        onPlayerRenderingStart();
    }

    final void onMediaPlayerPositionUpdate(int position, int duration) {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerPositionUpdate.........pos:" + position + "/" + duration);
        onPlayerPositionUpdate(position, duration);
    }

    final void onMediaPlayerBufferingStart() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerBufferingStart.........");
        onPlayerBufferingStart();
    }

    final void onMediaPlayerBufferingEnd() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerBufferingEnd.........");
        onPlayerBufferingEnd();
    }

    final void onMediaPlayerPaused() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerPaused.........");
        onPlayerPaused();
    }

    final void onMediaPlayerCompletion() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerCompletion.........");
        onPlayerCompletion();
    }

    final void onMediaPlayerStopped() {
        if (L.isEnable()) L.i(initTag(), "onMediaPlayerStopped.........");
        onPlayerStopped();
    }

    /**
     * Called to indicate an info or a warning.
     *
     * what:    the type of info or warning.
     * <li>{@link MediaPlayer#MEDIA_INFO_UNKNOWN}
     * <li>{@link MediaPlayer#MEDIA_INFO_VIDEO_TRACK_LAGGING}
     * <li>{@link MediaPlayer#MEDIA_INFO_VIDEO_RENDERING_START}
     * <li>{@link MediaPlayer#MEDIA_INFO_BUFFERING_START}
     * <li>{@link MediaPlayer#MEDIA_INFO_BUFFERING_END}
     * <li><code>MEDIA_INFO_NETWORK_BANDWIDTH (703)</code> -
     * bandwidth information is available (as <code>extra</code> kbps)
     * <li>{@link MediaPlayer#MEDIA_INFO_BAD_INTERLEAVING}
     * <li>{@link MediaPlayer#MEDIA_INFO_NOT_SEEKABLE}
     * <li>{@link MediaPlayer#MEDIA_INFO_METADATA_UPDATE}
     * <li>{@link MediaPlayer#MEDIA_INFO_UNSUPPORTED_SUBTITLE}
     * <li>{@link MediaPlayer#MEDIA_INFO_SUBTITLE_TIMED_OUT}
     * </ul>
     * extra: an extra code, specific to the info. Typically implementation dependent.
     */
    final void onMediaPlayerInfo(int what, int extra) {
        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
            onMediaPlayerRenderingStart();

        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
            onMediaPlayerBufferingStart();

        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            onMediaPlayerBufferingEnd();

        } else if (extra == MediaPlayer.MEDIA_ERROR_IO
                || extra == MediaPlayer.MEDIA_ERROR_MALFORMED
                || extra == MediaPlayer.MEDIA_ERROR_UNSUPPORTED
                || extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
            reloadMediaPlayer();
        }
    }

    /**
     * what :
     * MEDIA_ERROR_UNKNOWN（1），未指定的错误
     * MEDIA_ERROR_SERVER_DIED（100），media server died，需要释放当前media player，创建一个新的mediaPlayer
     *
     * extra :
     * MEDIA_ERROR_IO（-1004），io错误，文件或者网络相关错误
     * MEDIA_ERROR_MALFORMED（-1007），音视频格式错误，demux或解码错误
     * MEDIA_ERROR_UNSUPPORTED（-1010），不支持的音视频格式
     * MEDIA_ERROR_TIMED_OUT（-110），操作超时，通常是超过了3—5秒
     * MEDIA_ERROR_SYSTEM（ -2147483648），系统底层错误
     */
    final void onMediaPlayerError(int what, int extra) {
        if (what == ERROR_CODE_INIT) {
            if (L.isEnable()) L.e(initTag(), "onPlayerError.........what:" + what + ", extra:" + extra);
            onPlayerError();

        } else if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED
                || extra == MediaPlayer.MEDIA_ERROR_IO
                || extra == MediaPlayer.MEDIA_ERROR_MALFORMED
                || extra == MediaPlayer.MEDIA_ERROR_UNSUPPORTED
                || extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
            reloadMediaPlayer();
        }
    }

    private void reloadMediaPlayer() {
        mPlayer.seekTo(mPlayer.getCurrentPosition());
        mPlayer.reload();
    }

    private String initTag() {
        return getClass().getSimpleName();
    }
}
