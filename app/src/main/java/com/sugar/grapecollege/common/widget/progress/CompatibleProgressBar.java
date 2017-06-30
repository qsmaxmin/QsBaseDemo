package com.sugar.grapecollege.common.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sugar.grapecollege.R;
import com.sugar.grapecollege.common.download.callback.DownloadCallback;
import com.sugar.grapecollege.common.greendao.model.DownloadState;
import com.sugar.grapecollege.common.greendao.model.ModelProduct;
import com.sugar.grapecollege.common.widget.textview.ProgressTextView;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/12 10:40
 * @Description progressbar
 */

public class CompatibleProgressBar extends RelativeLayout implements DownloadCallback {

    private String initText     = getResources().getString(R.string.download);
    private String pauseText    = getResources().getString(R.string.go_on);
    private String completeText = getResources().getString(R.string.complete);
    private String waitText     = getResources().getString(R.string.waiting);
    private float  textSize     = 12f;

    private int                           progress;
    private ProgressTextView              textView;
    private CompatibleProgressBarDrawable progressDrawable;
    private ModelProduct                  modelProduct;


    public CompatibleProgressBar(Context context) {
        super(context);
        init(null);
    }

    public CompatibleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CompatibleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void init(AttributeSet attrs) {
        setClickable(true);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CompatibleProgressBar);
        int bgColor = typedArray.getColor(R.styleable.CompatibleProgressBar_cpb_backgroundColor, Color.WHITE);
        int fillColor = typedArray.getColor(R.styleable.CompatibleProgressBar_cpb_fillColor, getResources().getColor(R.color.color_accent));
        int strokeColor = typedArray.getColor(R.styleable.CompatibleProgressBar_cpb_strokeColor, getResources().getColor(R.color.color_gray));
        float radius = typedArray.getDimension(R.styleable.CompatibleProgressBar_cpb_radius, 15);
        float strokeWidth = typedArray.getDimension(R.styleable.CompatibleProgressBar_cpb_strokeWidth, 1);
        typedArray.recycle();

        setGravity(Gravity.CENTER);
        setDuplicateParentStateEnabled(true);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View progressView = new View(getContext());

        progressDrawable = new CompatibleProgressBarDrawable(radius, fillColor, bgColor, strokeColor, strokeWidth);
        progressView.setBackgroundDrawable(progressDrawable);
        progressView.setLayoutParams(params);

        textView = new ProgressTextView(getContext());
        textView.setStartColor(bgColor);
        textView.setEndColor(fillColor);
        textView.setTextColor(fillColor);
        textView.setText(initText);
        textView.setTextSize(textSize);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);
        textView.setDuplicateParentStateEnabled(true);

        addView(progressView);
        addView(textView);
    }

    private void setViewState(String fontId, int progress, String text) {
        if (!TextUtils.isEmpty(fontId) && fontId.equals(getTag())) {
            setViewProgress(progress);
            if (textView != null) {
                textView.setText(text);
            }
        }
    }

    private void setViewProgress(int progress) {
        this.progress = progress;
        progress = progress < 0 ? 0 : (progress > 100 ? 100 : progress);
        if (progressDrawable != null) progressDrawable.setRatio(progress / 100f);
        if (textView != null) textView.setRatio(progress / 100f);
    }

    public void setData(ModelProduct modelProduct) {
        this.modelProduct = modelProduct;
        refreshView(modelProduct);
    }

    public void setOnClickListener(final OnClickListener listener) {
        if (textView != null && listener != null) {
            textView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(CompatibleProgressBar.this);
                }
            });
        }
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        if (textView != null) textView.setTextSize(textSize);
    }


    public void setTextContent(String initText, String pauseText, String completeText, String waitText) {
        if (!TextUtils.isEmpty(initText)) {
            this.initText = initText;
        }
        if (!TextUtils.isEmpty(pauseText)) {
            this.pauseText = pauseText;
        }
        if (!TextUtils.isEmpty(completeText)) {
            this.completeText = completeText;
        }
        if (!TextUtils.isEmpty(waitText)) {
            this.waitText = waitText;
        }
        refreshView(modelProduct);
    }

    private void refreshView(ModelProduct modelProduct) {
        if (modelProduct != null) {
            this.progress = modelProduct.getSize() <= 0 ? 0 : (int) (modelProduct.getTempSize() * 100 / modelProduct.getSize());
            setTag(modelProduct.getProductId());
            DownloadState state = modelProduct.getDownloadState();
            if (state != null) switch (state) {
                case DOWNLOAD_INIT:
                    setViewState(modelProduct.getProductId(), 0, initText);
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

    @Override public void onDownloadConnecting(String fontId) {
        setViewState(fontId, progress, progress + "%");
    }

    @Override public void onDownloadStart(String fontId) {
        setViewState(fontId, 0, 0 + "%");
    }

    @Override public void onDownloadPause(String fontId) {
        setViewState(fontId, progress, pauseText);
    }

    @Override public void onDownloadRestart(String fontId, int percent) {
        setViewState(fontId, percent, percent + "%");
    }

    @Override public void onDownloading(String fontId, int progress) {
        setViewState(fontId, progress, progress + "%");
    }

    @Override public void onDownloadComplete(String fontId) {
        setViewState(fontId, 100, completeText);
    }

    @Override public void onDownloadFailed(String fontId, String message) {
        setViewState(fontId, 0, initText);
    }

    @Override public void onDownloadWaite(String fontId) {
        setViewState(fontId, progress, waitText);
    }

    @Override public void onDownloadCancel(String fontId) {
        setViewState(fontId, 0, initText);
    }
}
