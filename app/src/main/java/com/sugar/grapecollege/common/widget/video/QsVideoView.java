package com.sugar.grapecollege.common.widget.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.qsmaxmin.qsbase.common.log.L;

import java.util.Map;


/**
 * @CreateBy qsmaxmin
 * @Date 6/19/21  6:23 PM
 * @Description
 */
public class QsVideoView extends FrameLayout implements IMediaPlayer {
    private static final int STATE_ERROR              = -1;
    private static final int STATE_IDLE               = 0;
    private static final int STATE_PREPARING          = 1;
    private static final int STATE_PREPARED           = 2;
    private static final int STATE_PLAYING            = 3;
    private static final int STATE_PAUSED             = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;

    private final String              TAG = "QsVideoView";
    private final SurfaceView         mSurfaceView;
    private       int                 mCurrentState;
    private       int                 mTargetState;
    private       int                 mVideoWidth;
    private       int                 mVideoHeight;
    private       int                 mSurfaceWidth;
    private       int                 mSurfaceHeight;
    private       int                 mCurrentBufferPercentage;
    private       int                 mSeekWhenPrepared;
    private       float               mSpeed;
    private       AbsMediaController  mMediaController;
    private       Uri                 mUri;
    private       Map<String, String> mHeaders;
    private       SurfaceHolder       mSurfaceHolder;
    private       MediaPlayer         mMediaPlayer;

    private final MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                mSurfaceView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                requestLayout();
            }
        }
    };

    private final MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override public void onPrepared(MediaPlayer mp) {
            mCurrentState = STATE_PREPARED;
            if (mMediaController != null) {
                mMediaController.onMediaPlayerPrepared();
            }
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();

            if (mSeekWhenPrepared != 0) {
                seekTo(mSeekWhenPrepared);
            }
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                mSurfaceView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                if (mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
                    if (mTargetState == STATE_PLAYING) {
                        start();
                    }
                }
            } else if (mTargetState == STATE_PLAYING) {
                start();
            }
        }
    };

    private final MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override public void onCompletion(MediaPlayer mp) {
            mCurrentState = STATE_PLAYBACK_COMPLETED;
            mTargetState = STATE_PLAYBACK_COMPLETED;
            if (mMediaController != null) {
                mMediaController.onMediaPlayerCompletion();
            }
        }
    };

    private final MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener() {
        @Override public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (mMediaController != null) {
                mMediaController.onMediaPlayerInfo(what, extra);
            }
            return true;
        }
    };

    private final MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {
        @Override public boolean onError(MediaPlayer mp, int what, int extra) {
            L.e(TAG, "Error: " + what + "," + extra);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            if (mMediaController != null) {
                mMediaController.onMediaPlayerError(what, extra);
            }
            return true;
        }
    };

    private final MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override public void onBufferingUpdate(MediaPlayer mp, int percent) {
            mCurrentBufferPercentage = percent;
        }
    };

    public QsVideoView(Context context) {
        this(context, null, 0);
    }

    public QsVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QsVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mVideoWidth = 0;
        mVideoHeight = 0;
        mSurfaceView = new VideoView(getContext());
        mSurfaceView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                mSurfaceWidth = w;
                mSurfaceHeight = h;
                boolean isValidState = (mTargetState == STATE_PLAYING);
                boolean hasValidSize = (mVideoWidth == w && mVideoHeight == h);
                if (mMediaPlayer != null && isValidState && hasValidSize) {
                    if (mSeekWhenPrepared != 0) {
                        seekTo(mSeekWhenPrepared);
                    }
                    start();
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceHolder = holder;
                openVideo();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceHolder = null;
                releaseMediaPlayer(true);
                if (mMediaController != null) {
                    mMediaController.onMediaPlayerStopped();
                }
            }
        });
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();

        mCurrentState = STATE_IDLE;
        mTargetState = STATE_IDLE;
        addView(mSurfaceView);
    }


    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int viewWidth = right - left;
        int viewHeight = bottom - top;
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            float scale = Math.min((float) viewWidth / mVideoWidth, (float) viewHeight / mVideoHeight);

            int surfaceWidth = (int) (mVideoWidth * scale);
            int surfaceHeight = (int) (mVideoHeight * scale);

            int surfaceLeft = (viewWidth - surfaceWidth) / 2;
            int surfaceTop = (viewHeight - surfaceHeight) / 2;
            mSurfaceView.layout(surfaceLeft, surfaceTop, surfaceLeft + surfaceWidth, surfaceTop + surfaceHeight);
        }

        if (mMediaController != null) {
            mMediaController.getContentView().layout(0, 0, viewWidth, viewHeight);
        }
    }

    @Override public CharSequence getAccessibilityClassName() {
        return QsVideoView.class.getName();
    }

    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    public void setVideoURI(Uri uri, Map<String, String> headers) {
        mUri = uri;
        mHeaders = headers;
        mSeekWhenPrepared = 0;
        openVideo();
    }

    private void openVideo() {
        if (mUri == null || mSurfaceHolder == null) {
            return;
        }
        releaseMediaPlayer(false);
        try {
            mCurrentBufferPercentage = 0;
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnInfoListener(mInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            mMediaPlayer.setDataSource(getContext(), mUri, mHeaders);
            mMediaPlayer.setDisplay(mSurfaceHolder);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.prepareAsync();
            mCurrentState = STATE_PREPARING;
            if (mMediaController != null) mMediaController.onMediaPlayerLoading();

        } catch (Exception e) {
            L.e(TAG, "Unable to open content: " + mUri, e);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            if (mMediaController != null) {
                mMediaController.onMediaPlayerError(MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            }
        }
    }

    public void setMediaController(AbsMediaController mediaController) {
        if (mMediaController != null) {
            removeView(mMediaController.getContentView());
        }
        mMediaController = mediaController;
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(this);
        View contentView = mediaController.getContentView();
        if (contentView != this && contentView.getParent() == null) {
            addView(contentView);
        }
    }


    public void stopPlayback() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            mTargetState = STATE_IDLE;

            if (mMediaController != null) {
                mMediaController.onMediaPlayerStopped();
            }
        }
    }

    private void releaseMediaPlayer(boolean clearTargetState) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            if (clearTargetState) {
                mTargetState = STATE_IDLE;
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override public boolean onTouchEvent(MotionEvent ev) {
        return (mMediaController != null && mMediaController.onTouchEvent(ev))
                || super.onTouchEvent(ev);
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (mMediaController != null && mMediaController.onKeyDown(keyCode, event))
                || super.onKeyDown(keyCode, event);
    }

    @Override public void start() {
        if (isInPlaybackState()) {
            setSpeed(mSpeed);
            mMediaPlayer.start();
            mCurrentState = STATE_PLAYING;
            if (mMediaController != null) {
                mMediaController.onMediaPlayerStarted();
            }
        }
        mTargetState = STATE_PLAYING;
    }

    @Override public void pause() {
        if (isInPlaybackState()) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mCurrentState = STATE_PAUSED;
                if (mMediaController != null) {
                    mMediaController.onMediaPlayerPaused();
                }
            }
        }
        mTargetState = STATE_PAUSED;
    }

    @Override public void reload() {
        openVideo();
    }

    @Override public int getDuration() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * @param position milliseconds
     */
    @Override public void seekTo(int position) {
        if (isInPlaybackState()) {
            mMediaPlayer.seekTo(position);
            mSeekWhenPrepared = 0;
            if (mMediaController != null) {
                mMediaController.onMediaPlayerSeekComplete(position);
            }
        } else {
            mSeekWhenPrepared = position;
        }
    }

    @Override public boolean setSpeed(float speed) {
        if (speed <= 0 || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return false;
        } else {
            if (isInPlaybackState()) {
                PlaybackParams params = mMediaPlayer.getPlaybackParams();
                params.setSpeed(speed);
                mMediaPlayer.setPlaybackParams(params);
            }
            mSpeed = speed;
            return true;
        }
    }

    @Override public float getSpeed() {
        return mSpeed <= 0 ? 1 : mSpeed;
    }

    @Override public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    @Override public boolean isInPlaybackState() {
        return (mMediaPlayer != null &&
                mCurrentState != STATE_ERROR &&
                mCurrentState != STATE_IDLE &&
                mCurrentState != STATE_PREPARING);
    }

    @Override public int getBufferPercentage() {
        if (mMediaPlayer != null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }
}
