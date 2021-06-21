package com.sugar.grapecollege.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.qsmaxmin.qsbase.common.widget.toast.QsToast;
import com.qsmaxmin.qsbase.mvvm.MvActivity;
import com.sugar.grapecollege.databinding.ActivityTestVideoBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @CreateBy qsmaxmin
 * @Date 6/19/21  6:18 PM
 * @Description
 */
public class TestVideoActivity extends MvActivity {
    private ActivityTestVideoBinding contentBinding;

    @Override public View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
        contentBinding = ActivityTestVideoBinding.inflate(inflater, parent, false);
        contentBinding.setOwner(this);
        return contentBinding.getRoot();
    }

    @Override public void initData(@Nullable Bundle savedInstanceState) {
        loadVideo();
    }

    /**
     * http://vjs.zencdn.net/v/oceans.mp4
     * https://media.w3.org/2010/05/sintel/trailer.mp4
     * http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4
     * http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4
     */
    private void loadVideo() {
        contentBinding.playerView.setShowNextButton(false);
        contentBinding.playerView.setShowPreviousButton(false);

        contentBinding.playerView.setShowFastForwardButton(true);
        contentBinding.playerView.setShowBuffering(StyledPlayerView.SHOW_BUFFERING_ALWAYS);
        contentBinding.playerView.setShowRewindButton(true);

        contentBinding.playerView.setShowShuffleButton(false);

        contentBinding.playerView.setShowVrButton(false);
        contentBinding.playerView.setControllerShowTimeoutMs(4000);

        contentBinding.playerView.setShowMultiWindowTimeBar(true);

        contentBinding.playerView.setControllerOnFullScreenModeChangedListener(new StyledPlayerControlView.OnFullScreenModeChangedListener() {
            @Override public void onFullScreenModeChanged(boolean isFullScreen) {
                QsToast.show("isFullScreen:"+isFullScreen);

                ViewGroup.LayoutParams lp = contentBinding.playerView.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                contentBinding.playerView.requestLayout();
            }
        });

        SimpleExoPlayer player = new SimpleExoPlayer.Builder(getContext()).build();
        contentBinding.playerView.setPlayer(player);


        String url = "https://media.w3.org/2010/05/sintel/trailer.mp4";
        MediaItem mediaItem = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }
}
