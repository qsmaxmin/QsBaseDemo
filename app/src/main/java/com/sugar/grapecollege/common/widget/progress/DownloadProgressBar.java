package com.sugar.grapecollege.common.widget.progress;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.download.callback.DownloadCallback;
import com.sugar.grapecollege.common.greendao.model.DownloadState;
import com.sugar.grapecollege.common.greendao.model.ModelProduct;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/10 17:40
 * @Description
 */

public class DownloadProgressBar extends RelativeLayout implements DownloadCallback {

    private int      progress;
    private TextView textView;
    private View     progressView;

    private float textSize = 12f;

    public DownloadProgressBar(Context context) {
        super(context);
        init();
    }

    public DownloadProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DownloadProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void init() {
        setGravity(Gravity.CENTER);
        setDuplicateParentStateEnabled(true);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View background = new View(getContext());
        background.setBackgroundResource(R.drawable.shape_rect_transparent_accent_stroke);
        background.setLayoutParams(params);

        progressView = new View(getContext());
        progressView.setBackgroundResource(R.drawable.shape_rect_accent_2radius);
        progressView.setLayoutParams(params);

        textView = new TextView(getContext());
        textView.setTextColor(Color.BLACK);
        textView.setText(getResources().getString(R.string.download));
        textView.setTextSize(textSize);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);
        textView.setDuplicateParentStateEnabled(true);

        addView(background);
        addView(progressView);
        addView(textView);
    }

    private void setViewState(String fontId, int progress, String text, int colorSourceId) {
        if (!TextUtils.isEmpty(fontId) && fontId.equals(getTag())) {
            if (progress == 0 || this.progress != progress) {
                this.progress = progress;
                setViewProgress(progress);
            }
            if (textView != null) {
                textView.setText(text);
                textView.setTextColor(getResources().getColor(colorSourceId));
            }
        }
    }

    private void setViewProgress(int progress) {
        if (progressView != null) {
            float v = progress / 100f;
            int width = (int) (getWidth() * v);
            progressView.layout(0, 0, width, getHeight());
            progressView.invalidate();
        }
    }

    public void setData(ModelProduct modelProduct) {
        if (modelProduct != null) {
            setTag(modelProduct.getProductId());
            DownloadState state = modelProduct.getDownloadState();
            if (state != null) {
                switch (state) {
                    case DOWNLOAD_INIT:
                        onDownloadStart(modelProduct.getProductId());
                        break;
                    case DOWNLOAD_ING:
                        onDownloading(modelProduct.getProductId(), (modelProduct.getSize() > 0) ? (int) (modelProduct.getTempSize() * 100 / modelProduct.getSize()) : 0);
                        break;
                    case DOWNLOAD_PAUSE:
                        onDownloadPause(modelProduct.getProductId());
                        break;
                    case DOWNLOAD_WAIT:
                        onDownloadWaite(modelProduct.getProductId());
                        break;
                    case DOWNLOAD_COMPLETE:
                        onDownloadComplete(modelProduct.getProductId());
                        break;
                }
            }
        }
    }

    public void setOnClickListener(final OnClickListener listener) {
        if (textView != null && listener != null) {
            textView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(DownloadProgressBar.this);
                }
            });
        }
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        if (textView != null) textView.setTextSize(textSize);
    }


    @Override public void onDownloadConnecting(String fontId) {
        setViewState(fontId, 0, getResources().getString(R.string.connecting), R.color.color_black);
    }

    @Override public void onDownloadStart(String fontId) {
        setViewState(fontId, 0, 0 + "%", R.color.color_black);
    }

    @Override public void onDownloadPause(String fontId) {
        setViewState(fontId, progress, getResources().getString(R.string.download), R.color.color_black);
    }

    @Override public void onDownloadRestart(String fontId, int percent) {
        setViewState(fontId, percent, percent + "%", R.color.color_black);
    }

    @Override public void onDownloading(String fontId, int progress) {
        setViewState(fontId, progress, progress + "%", R.color.color_black);
    }

    @Override public void onDownloadComplete(String fontId) {
        setViewState(fontId, 100, getResources().getString(R.string.complete), R.color.color_black);
    }

    @Override public void onDownloadFailed(String fontId, String message) {
        setViewState(fontId, 0, getResources().getString(R.string.download), R.color.color_black);
    }

    @Override public void onDownloadWaite(String fontId) {
        setViewState(fontId, 0, getResources().getString(R.string.waiting), R.color.color_black);
    }

    @Override public void onDownloadCancel(String fontId) {
        setViewState(fontId, 0, getResources().getString(R.string.download), R.color.color_black);
    }
}
