package com.sugar.grapecollege.common.widget.video;

import android.content.Context;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.List;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/6/25 11:06
 * @Description
 */
class SimpleMediaPlayer extends SimpleExoPlayer {

    static SimpleMediaPlayer create(Context context) {
        return new SimpleMediaPlayer(new Builder(context));
    }

    private SimpleMediaPlayer(Builder builder) {
        super(builder);
    }

    @Override public void setMediaItems(List<MediaItem> mediaItems, boolean resetPosition) {
        super.setMediaItems(mediaItems, resetPosition);
    }
}
