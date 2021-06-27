package com.sugar.grapecollege.common.widget.video;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.sugar.grapecollege.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.google.android.exoplayer2.Player.EVENT_PLAYBACK_PARAMETERS_CHANGED;
import static com.google.android.exoplayer2.Player.EVENT_PLAYBACK_STATE_CHANGED;
import static com.google.android.exoplayer2.Player.EVENT_PLAY_WHEN_READY_CHANGED;
import static com.google.android.exoplayer2.Player.EVENT_POSITION_DISCONTINUITY;
import static com.google.android.exoplayer2.Player.EVENT_REPEAT_MODE_CHANGED;
import static com.google.android.exoplayer2.Player.EVENT_TIMELINE_CHANGED;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/6/25 9:35
 * @Description
 */
public class PlayerControlView extends FrameLayout {
    private static final int MAX_UPDATE_INTERVAL_MS = 1_000;

    private Timeline.Window                 window;
    private Player                          mPlayer;
    private ComponentListener               componentListener;
    private HideAction                      hideAction;
    private TimeFormatter                   formatter;
    private DefaultTimeBar                  timeBar;
    private ImageView                       playPauseButton;
    private TextView                        positionView;
    private TextView                        durationView;
    private ImageView                       repeatToggleButton;
    private View                            settingsButton;
    private ImageView                       fullScreenButton;
    private UpdateProgressAction            updateProgressAction;
    private Resources                       resources;
    private int                             repeatToggleModes;
    private DefaultControlDispatcher        controlDispatcher;
    private SettingView                     settingsWindow;
    private OnFullScreenButtonClickListener fullScreenButtonClickListener;
    private boolean                         isFullScreen;
    private boolean                         scrubbing;

    public PlayerControlView(@NonNull Context context) {
        super(context);
        init();
    }

    public PlayerControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        repeatToggleModes = RepeatModeUtil.REPEAT_TOGGLE_MODE_NONE;
        resources = getContext().getResources();
        controlDispatcher = new DefaultControlDispatcher();

        inflate(getContext(), R.layout.view_video_controller, this);
        timeBar = findViewById(R.id.exo_progress);
        playPauseButton = findViewById(R.id.exo_play_pause);
        positionView = findViewById(R.id.exo_position);
        durationView = findViewById(R.id.exo_duration);
        repeatToggleButton = findViewById(R.id.exo_repeat_toggle);
        settingsButton = findViewById(R.id.exo_settings);
        fullScreenButton = findViewById(R.id.exo_fullscreen);

        window = new Timeline.Window();

        componentListener = new ComponentListener();
        formatter = new TimeFormatter();
        hideAction = new HideAction();
        updateProgressAction = new UpdateProgressAction();

        settingsWindow = new SettingView(getContext());
        settingsWindow.setOnDismissListener(componentListener);
        settingsWindow.setOnSpeedCheckChangedListener(componentListener);

        playPauseButton.setOnClickListener(componentListener);
        repeatToggleButton.setOnClickListener(componentListener);
        settingsButton.setOnClickListener(componentListener);
        fullScreenButton.setOnClickListener(componentListener);
    }


    private void updateAll() {
        updatePlayPauseButton();
        updateRepeatModeButton();
        updatePlaybackSpeedList();
        updateTimeline();
    }

    void setPlayer(Player player) {
        if (mPlayer == player) {
            return;
        }
        if (mPlayer != null) {
            mPlayer.removeListener(componentListener);
        }
        mPlayer = player;
        if (player != null) {
            player.addListener(componentListener);
        }
        updateAll();
        updateProgressAction.startLooper();
    }

    void hide() {
        setVisibility(GONE);
    }

    void show() {
        setVisibility(VISIBLE);
    }

    void showTimeout() {
        show();
        hideAction.hideDelayed();
    }

    boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    private void updateButton(boolean enabled, @Nullable View view) {
        if (view != null) {
            view.setEnabled(enabled);
            view.setAlpha(enabled ? 1f : 0.3f);
        }
    }

    private void updatePlaybackSpeedList() {
        if (mPlayer == null) return;
        settingsWindow.updateSelectedIndex(mPlayer.getPlaybackParameters().speed);
    }

    private void updateRepeatModeButton() {
        if (!isVisible()) return;
        if (mPlayer != null) {
            updateButton(true, repeatToggleButton);
            switch (mPlayer.getRepeatMode()) {
                case Player.REPEAT_MODE_OFF:
                    repeatToggleButton.setImageDrawable(Util.getDrawable(resources, R.mipmap.exo_icon_repeat_off));
                    break;
                case Player.REPEAT_MODE_ONE:
                    repeatToggleButton.setImageDrawable(Util.getDrawable(resources, R.mipmap.exo_icon_repeat_one));
                    break;
                case Player.REPEAT_MODE_ALL:
                    repeatToggleButton.setImageDrawable(Util.getDrawable(resources, R.mipmap.exo_icon_repeat_all));
                    break;
                default:
            }
        } else {
            updateButton(false, repeatToggleButton);
            repeatToggleButton.setImageDrawable(Util.getDrawable(resources, R.mipmap.exo_icon_repeat_off));
        }
    }

    private boolean shouldShowPauseButton() {
        return mPlayer != null
                && mPlayer.getPlaybackState() != Player.STATE_ENDED
                && mPlayer.getPlaybackState() != Player.STATE_IDLE
                && mPlayer.getPlayWhenReady();
    }

    private void updatePlayPauseButton() {
        if (shouldShowPauseButton()) {
            playPauseButton.setImageDrawable(Util.getDrawable(resources, R.mipmap.exo_icon_pause));
        } else {
            playPauseButton.setImageDrawable(Util.getDrawable(resources, R.mipmap.exo_icon_play));
        }
    }

    private void updateTimeline() {
        long durationMs = 0;
        if (mPlayer != null) {
            Timeline timeline = mPlayer.getCurrentTimeline();
            if (!timeline.isEmpty()) {
                int windowIndex = mPlayer.getCurrentWindowIndex();
                timeline.getWindow(windowIndex, this.window);
                durationMs = window.getDurationMs();
            }
        }
        durationView.setText(formatter.getStringForTime(durationMs));
        timeBar.setDuration(durationMs);
        updateProgress();
    }

    private void updateProgress() {
        if (!isVisible()) return;
        long position = 0;
        long bufferedPosition = 0;
        if (mPlayer != null) {
            position = mPlayer.getCurrentPosition();
            bufferedPosition = mPlayer.getBufferedPosition();
        }
        if (!scrubbing) {
            positionView.setText(formatter.getStringForTime(position));
        }
        timeBar.setPosition(position);
        timeBar.setBufferedPosition(bufferedPosition);
    }

    private void dispatchPlayPause() {
        if (mPlayer != null) {
            int state = mPlayer.getPlaybackState();
            if (state == Player.STATE_IDLE || state == Player.STATE_ENDED || !mPlayer.getPlayWhenReady()) {
                dispatchPlay(mPlayer);
            } else {
                dispatchPause(mPlayer);
            }
        }
    }

    private void dispatchPlay(Player player) {
        if (player == null) return;
        int state = player.getPlaybackState();
        if (state == Player.STATE_IDLE) {
            controlDispatcher.dispatchPrepare(player);
        } else if (state == Player.STATE_ENDED) {
            seekTo(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
        }
        controlDispatcher.dispatchSetPlayWhenReady(player, true);
    }

    private void dispatchPause(Player player) {
        if (player == null) return;
        controlDispatcher.dispatchSetPlayWhenReady(player, false);
    }

    private boolean seekTo(Player player, int windowIndex, long positionMs) {
        return controlDispatcher.dispatchSeekTo(player, windowIndex, positionMs);
    }

    private void seekToTimeBarPosition(Player player, long positionMs) {
        if (player == null) return;
        int windowIndex = player.getCurrentWindowIndex();
        boolean dispatched = seekTo(player, windowIndex, positionMs);
        if (!dispatched) {
            updateProgress();
        }
    }

    private final class ComponentListener implements Player.Listener, TimeBar.OnScrubListener,
            OnClickListener, PopupWindow.OnDismissListener, SettingView.OnSpeedCheckChangedListener {

        @Override public void onScrubStart(TimeBar timeBar, long position) {
            scrubbing = true;
            positionView.setText(formatter.getStringForTime(position));
        }

        @Override public void onScrubMove(TimeBar timeBar, long position) {
            positionView.setText(formatter.getStringForTime(position));
        }

        @Override public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
            scrubbing = false;
            seekToTimeBarPosition(mPlayer, position);
        }

        @Override public void onEvents(@NonNull Player player, @NonNull Player.Events events) {
            if (events.containsAny(EVENT_PLAYBACK_STATE_CHANGED, EVENT_PLAY_WHEN_READY_CHANGED)) {
                updatePlayPauseButton();
            }
            if (events.containsAny(EVENT_POSITION_DISCONTINUITY, EVENT_TIMELINE_CHANGED)) {
                updateTimeline();
            }
            if (events.contains(EVENT_REPEAT_MODE_CHANGED)) {
                updateRepeatModeButton();
            }
            if (events.contains(EVENT_PLAYBACK_PARAMETERS_CHANGED)) {
                updatePlaybackSpeedList();
            }
        }

        @Override public void onClick(View view) {
            if (mPlayer == null) return;
            if (view == playPauseButton) {
                dispatchPlayPause();

            } else if (view == repeatToggleButton) {
                int nextRepeatMode = RepeatModeUtil.getNextRepeatMode(mPlayer.getRepeatMode(), repeatToggleModes);
                controlDispatcher.dispatchSetRepeatMode(mPlayer, nextRepeatMode);

            } else if (view == settingsButton) {
                if (settingsWindow.isShowing()) {
                    settingsWindow.dismiss();
                } else {
                    hideAction.cancel();
                    settingsWindow.showAtTop(PlayerControlView.this);
                }

            } else if (view == fullScreenButton) {
                if (fullScreenButtonClickListener != null) {
                    isFullScreen = !isFullScreen;
                    if (isFullScreen) {
                        fullScreenButton.setImageResource(R.mipmap.exo_icon_fullscreen_exit);
                    } else {
                        fullScreenButton.setImageResource(R.mipmap.exo_icon_fullscreen_enter);
                    }
                    fullScreenButtonClickListener.onFullScreenButtonClicked(isFullScreen);
                }
            }
        }

        @Override public void onDismiss() {
            if (mPlayer != null && mPlayer.getPlayWhenReady()) {

            } else {

            }
        }

        @Override public void onSpeedCheckChanged(float speed) {
            if (mPlayer != null) {
                mPlayer.setPlaybackSpeed(speed);
            }
        }
    }

    private class HideAction implements Runnable {
        @Override public void run() {
            hide();
        }

        void hideDelayed() {
            removeCallbacks(this);
            postDelayed(this, 4000);
        }

        void cancel() {
            removeCallbacks(this);
        }
    }

    boolean isFullScreen() {
        return isFullScreen;
    }

    void setOnFullScreenButtonClickListener(OnFullScreenButtonClickListener listener) {
        this.fullScreenButtonClickListener = listener;
    }

    interface OnFullScreenButtonClickListener {
        void onFullScreenButtonClicked(boolean fullScreen);
    }

    private class UpdateProgressAction implements Runnable {
        @Override public void run() {
            updateProgress();
            int playbackState = mPlayer == null ? Player.STATE_IDLE : mPlayer.getPlaybackState();
            if (mPlayer != null && mPlayer.isPlaying()) {
                long position = mPlayer.getCurrentPosition();
                long mediaTimeDelayMs = timeBar.getPreferredUpdateDelay();

                long mediaTimeUntilNextFullSecondMs = 1000 - position % 1000;
                mediaTimeDelayMs = Math.min(mediaTimeDelayMs, mediaTimeUntilNextFullSecondMs);

                float playbackSpeed = mPlayer.getPlaybackParameters().speed;
                long delayMs = playbackSpeed > 0 ? (long) (mediaTimeDelayMs / playbackSpeed) : MAX_UPDATE_INTERVAL_MS;

                delayMs = Util.constrainValue(delayMs, 200, MAX_UPDATE_INTERVAL_MS);
                postDelayed(this, delayMs);
            } else if (playbackState != Player.STATE_ENDED && playbackState != Player.STATE_IDLE) {
                postDelayed(this, MAX_UPDATE_INTERVAL_MS);
            }
        }

        void startLooper() {
            removeCallbacks(this);
            post(this);
        }
    }
}
