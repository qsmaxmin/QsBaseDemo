package com.sugar.grapecollege.common.widget.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.video.VideoSize;
import com.qsmaxmin.qsbase.common.log.L;
import com.sugar.grapecollege.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/6/23 10:15
 * @Description
 */
public class QsVideoView extends FrameLayout {
    private SurfaceView       surfaceView;
    private ImageView         artworkView;
    private ProgressBar       bufferingView;
    private TextView          errorMessageView;
    private PlayerControlView controllerView;
    private SimpleMediaPlayer player;
    private float             videoWidthHeightRatio;
    private ComponentListener componentListener;
    private View              vg_function;

    public QsVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public QsVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QsVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_video_player, this);
        surfaceView = findViewById(R.id.exo_surface);
        vg_function = findViewById(R.id.vg_function);
        artworkView = findViewById(R.id.exo_artwork);
        bufferingView = findViewById(R.id.exo_buffering);
        errorMessageView = findViewById(R.id.exo_error_message);
        controllerView = findViewById(R.id.exo_controller);

        componentListener = new ComponentListener();
        controllerView.setOnFullScreenButtonClickListener(componentListener);
        surfaceView.setOnClickListener(componentListener);

        updateBuffering();
        updateErrorMessage();
        updateControllerVisibility();
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (videoWidthHeightRatio != 0) {
            int viewWidth = right - left;
            int viewHeight = bottom - top;
            float viewWidthHeightRatio = (float) viewWidth / viewHeight;
            int videoHeight;
            int videoWidth;
            if (viewWidthHeightRatio > videoWidthHeightRatio) {
                videoHeight = viewHeight;
                videoWidth = (int) (videoHeight * videoWidthHeightRatio);
            } else {
                videoWidth = viewWidth;
                videoHeight = (int) (videoWidth / videoWidthHeightRatio);
            }
            int videoLeft = (viewWidth - videoWidth) / 2;
            int videoTop = (viewHeight - videoHeight) / 2;
            surfaceView.layout(videoLeft, videoTop, videoLeft + videoWidth, videoTop + videoHeight);
        } else {
            surfaceView.layout(0, 0, right - left, bottom - top);
        }
        vg_function.layout(0, 0, right - left, bottom - top);
    }

    public void startPlay(String url) {
        initPlayer();
        player.setPlayWhenReady(true);
        MediaItem mediaItem = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
        player.prepare();
    }

    public void release() {
        if (player != null) {
            player.release();
        }
    }

    private void initPlayer() {
        if (player == null) {
            player = SimpleMediaPlayer.create(getContext());
            controllerView.setPlayer(player);
            player.setVideoSurfaceView(surfaceView);

            updateBuffering();
            updateErrorMessage();
            updateControllerVisibility();
            player.addListener(componentListener);
        }
    }

    private void updateControllerVisibility() {
        if (player == null) {
            controllerView.hide();
        } else {
            if (shouldShowIndefinitely()) {
                controllerView.show();
            } else {
                controllerView.showTimeout();
            }
        }
    }

    private boolean shouldShowIndefinitely() {
        int playbackState = player.getPlaybackState();
        return !player.getCurrentTimeline().isEmpty()
                && (playbackState == Player.STATE_IDLE
                || playbackState == Player.STATE_ENDED
                || !player.getPlayWhenReady());
    }

    private void toggleControllerVisibility() {
        if (player == null) return;
        if (controllerView.isVisible()) {
            controllerView.hide();
        } else {

            if (shouldShowIndefinitely()) {
                controllerView.show();
            } else {
                controllerView.showTimeout();
            }
        }
    }

    private void updateErrorMessage() {
        if (player != null) {
            ExoPlaybackException error = player.getPlayerError();
            if (error != null) {
                errorMessageView.setVisibility(View.VISIBLE);
                errorMessageView.setText(getErrorMsg(error));
            } else {
                errorMessageView.setVisibility(GONE);
            }
        }
    }

    private void updateBuffering() {
        boolean showBufferingSpinner = player != null
                && player.getPlaybackState() == Player.STATE_BUFFERING
                && player.getPlayWhenReady();
        bufferingView.setVisibility(showBufferingSpinner ? View.VISIBLE : View.GONE);
    }

    private CharSequence getErrorMsg(ExoPlaybackException error) {
        if (error.type == ExoPlaybackException.TYPE_REMOTE) {
            return error.getMessage();
        } else if (error.type == ExoPlaybackException.TYPE_RENDERER) {
            return error.getRendererException().getMessage();
        } else if (error.type == ExoPlaybackException.TYPE_SOURCE) {
            return error.getSourceException().getMessage();
        } else if (error.type == ExoPlaybackException.TYPE_UNEXPECTED) {
            return error.getUnexpectedException().getMessage();
        }
        return "unKnow(" + error.type + ")";
    }

    private final class ComponentListener implements Player.Listener, OnClickListener, PlayerControlView.OnFullScreenButtonClickListener {

        private final EventPrinter eventPrinter;

        ComponentListener() {
            eventPrinter = new EventPrinter();
        }

        @Override public void onEvents(@NonNull Player player, @NonNull Player.Events events) {
            eventPrinter.printEvent(player, events);
        }

        @Override public void onCues(@NonNull List<Cue> cues) {

        }

        @Override public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
            L.i("QsVideoView", "onVideoSizeChanged....." + videoSize.width + ", " + videoSize.height + ", " + videoSize.pixelWidthHeightRatio);
            videoWidthHeightRatio = (float) videoSize.width / videoSize.height;
            requestLayout();
        }

        @Override public void onRenderedFirstFrame() {
//            artworkView.setVisibility(INVISIBLE);
        }

        @Override public void onPlaybackStateChanged(@Player.State int state) {
            updateBuffering();
            updateErrorMessage();
            updateControllerVisibility();
        }

        @Override
        public void onPlayWhenReadyChanged(boolean playWhenReady, @Player.PlayWhenReadyChangeReason int reason) {
            updateBuffering();
            updateControllerVisibility();
        }

        @Override public void onClick(View v) {
            toggleControllerVisibility();
        }

        @Override public void onFullScreenButtonClicked(boolean fullScreen) {
            if (fullScreen) {
                requestFullScreen();
            } else {
                restoreToOriginal();
            }
        }
    }

    private int w, h, ml, mt;

    private void requestFullScreen() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        w = lp.width;
        h = lp.height;
        if (lp instanceof MarginLayoutParams) {
            ml = ((MarginLayoutParams) lp).leftMargin;
            mt = ((MarginLayoutParams) lp).topMargin;
        }
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        requestLayout();
    }

    private void restoreToOriginal() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = w;
        lp.height = h;
        if (lp instanceof MarginLayoutParams) {
            ((MarginLayoutParams) lp).leftMargin = ml;
            ((MarginLayoutParams) lp).topMargin = mt;
        }
        requestLayout();
    }

}
