package com.sugar.grapecollege.common.widget.video;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.widget.progress.QsSeekBar;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 6/19/21  6:28 PM
 * @Description
 */
public class DefaultMediaController extends AbsMediaController {
    private final Runnable  hideRunnable;
    private       View      vg_function;
    private       View      vg_loading;
    private       View      vg_error;
    private       ImageView iv_play;
    private       QsSeekBar sk_progress;
    private       TextView  tv_speed;

    public DefaultMediaController() {
        hideRunnable = new Runnable() {
            @Override public void run() {
                hideFunctionView();
            }
        };
    }

    private View getFunctionView() {
        return vg_function;
    }

    @Override protected View onCreateContentView(LayoutInflater inflater, ViewGroup parent) {
        boolean inPlaybackState = getMediaPlayer().isInPlaybackState();

        View view = inflater.inflate(R.layout.qs_video_controller, parent, false);
        vg_function = view.findViewById(R.id.vg_function);
        vg_loading = view.findViewById(R.id.vg_loading);
        vg_error = view.findViewById(R.id.vg_error);

        iv_play = view.findViewById(R.id.iv_play);
        sk_progress = view.findViewById(R.id.sk_progress);
        tv_speed = view.findViewById(R.id.tv_speed);

        if (inPlaybackState) {
            int current = getMediaPlayer().getCurrentPosition();
            int duration = getMediaPlayer().getDuration();
            float progress = (sk_progress.getMax() - sk_progress.getMin()) * current / duration;
            sk_progress.setProgress(sk_progress.getMin() + progress);
        }

        sk_progress.setOnSeekBarChangeListener(new QsSeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(QsSeekBar seekBar, float progress, boolean fromUser) {
            }

            @Override public void onStartTrackingTouch(QsSeekBar seekBar) {
            }

            @Override public void onStopTrackingTouch(QsSeekBar seekBar) {
                float progress = seekBar.getProgress();

            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (v == iv_play) {
                    if (getMediaPlayer().isPlaying()) {
                        getMediaPlayer().pause();
                    } else {
                        getMediaPlayer().start();
                    }
                } else if (v == tv_speed) {

                }
            }
        };
        iv_play.setOnClickListener(listener);
        tv_speed.setOnClickListener(listener);

        return view;
    }

    @Override public void onMediaPlayerLoading() {
        vg_loading.setVisibility(View.VISIBLE);
    }

    @Override public void onMediaPlayerPrepared() {

    }

    @Override public void onMediaPlayerStarted() {
        iv_play.setImageResource(android.R.drawable.ic_media_pause);
    }

    @Override public void onMediaPlayerPaused() {
        iv_play.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override public void onMediaPlayerError() {

    }

    @Override public void onMediaPlayerSeekComplete(int position) {

    }

    @Override public void onMediaPlayerCompletion() {

    }

    @Override public void onMediaPlayerStopped() {

    }

    public void hideFunctionView() {
        getFunctionView().setVisibility(View.GONE);
    }

    private void showFunctionView() {
        getFunctionView().setVisibility(View.VISIBLE);
    }

    private void showFunctionView(long timeout) {
        showFunctionView();
        removeCallback(hideRunnable);
        postDelayed(hideRunnable, timeout);
    }

    private void toggleFunctionViewVisibility() {
        if (getFunctionView().getVisibility() == View.VISIBLE) {
            hideFunctionView();
        } else {
            showFunctionView();
        }
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK &&
                keyCode != KeyEvent.KEYCODE_VOLUME_UP &&
                keyCode != KeyEvent.KEYCODE_VOLUME_DOWN &&
                keyCode != KeyEvent.KEYCODE_VOLUME_MUTE &&
                keyCode != KeyEvent.KEYCODE_MENU &&
                keyCode != KeyEvent.KEYCODE_CALL &&
                keyCode != KeyEvent.KEYCODE_ENDCALL;

        if (isKeyCodeSupported && getMediaPlayer().isInPlaybackState()) {
            if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
                if (getMediaPlayer().isPlaying()) {
                    getMediaPlayer().pause();
                    showFunctionView();
                } else {
                    getMediaPlayer().start();
                    hideFunctionView();
                }
                return true;

            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
                if (!getMediaPlayer().isPlaying()) {
                    getMediaPlayer().start();
                    hideFunctionView();
                }
                return true;

            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
                if (getMediaPlayer().isPlaying()) {
                    getMediaPlayer().pause();
                    showFunctionView();
                }
                return true;

            } else {
                toggleFunctionViewVisibility();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
