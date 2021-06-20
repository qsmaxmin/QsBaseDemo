package com.sugar.grapecollege.common.widget.video;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @CreateBy qsmaxmin
 * @Date 6/19/21  6:53 PM
 * @Description
 */
public abstract class AbsMediaController {
    private View         mContentView;
    private IMediaPlayer mPlayer;

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

    public final boolean removeCallback(Runnable action) {
        return mContentView.removeCallbacks(action);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    protected abstract View onCreateContentView(LayoutInflater inflater, ViewGroup parent);

    public abstract void onMediaPlayerLoading();

    public abstract void onMediaPlayerPrepared();

    public abstract void onMediaPlayerStarted();

    public abstract void onMediaPlayerPaused();

    public abstract void onMediaPlayerSeekComplete(int position);

    public abstract void onMediaPlayerCompletion();

    public abstract void onMediaPlayerStopped();

    public abstract void onMediaPlayerError();

    public final void onMediaPlayerInfo(int what, int extra) {

    }

    public final void onMediaPlayerError(int what, int extra) {

        int currentPosition = mPlayer.getCurrentPosition();
        mPlayer.seekTo(currentPosition);
        mPlayer.reload();
    }
}
