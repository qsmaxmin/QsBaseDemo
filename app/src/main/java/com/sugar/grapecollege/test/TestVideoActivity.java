package com.sugar.grapecollege.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        String url = "https://media.w3.org/2010/05/sintel/trailer.mp4";
        contentBinding.playerView.startPlay(url);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        contentBinding.playerView.release();
    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }
}
