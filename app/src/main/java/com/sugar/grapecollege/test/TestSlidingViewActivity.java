package com.sugar.grapecollege.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsmaxmin.annotation.thread.ThreadPoint;
import com.qsmaxmin.annotation.thread.ThreadType;
import com.qsmaxmin.qsbase.common.widget.viewpager.autoscroll.AutoScrollViewPager;
import com.qsmaxmin.qsbase.common.widget.viewpager.autoscroll.CirclePageIndicator;
import com.qsmaxmin.qsbase.common.widget.viewpager.autoscroll.InfinitePagerAdapter;
import com.qsmaxmin.qsbase.mvvm.MvPullListActivity;
import com.qsmaxmin.qsbase.mvvm.adapter.MvListAdapterItem;
import com.sugar.grapecollege.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/3/16 10:32
 * @Description
 */
public class TestSlidingViewActivity extends MvPullListActivity<String> {

    @Override public int actionbarLayoutId() {
        return R.layout.actionbar_title_edit;
    }

    @Override public int getHeaderLayout() {
        return super.getHeaderLayout();
    }

    @Override public View onCreateListHeaderView(@NonNull LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.header_main_fragment, null);
        AutoScrollViewPager banner = view.findViewById(R.id.banner);
        CirclePageIndicator indicator = view.findViewById(R.id.page_indicator);

        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmaps.add(bitmap);
        bitmaps.add(bitmap);
        bitmaps.add(bitmap);
        bitmaps.add(bitmap);

        InfinitePagerAdapter adapter = new InfinitePagerAdapter();
        adapter.setData(bitmaps);
        banner.setAdapter(adapter);
        indicator.setViewPager(banner);
        return view;
    }

    @NonNull @Override public MvListAdapterItem<String> getListAdapterItem(int type) {
        return new TestAdapterItem();
    }

    @Override public void initData(@Nullable Bundle savedInstanceState) {
        requestData(false);
    }

    @Override public void onRefresh() {
        requestData(false);
    }

    @Override public void onLoad() {
        requestData(true);
    }

    @ThreadPoint(ThreadType.HTTP)
    private void requestData(boolean loadingMore) {
        SystemClock.sleep(2000);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("哈哈哈" + i);
        }
        if (loadingMore) {
            addData(strings);
        } else {
            setData(strings);
        }
    }

    private static class TestAdapterItem extends MvListAdapterItem<String> {
        TextView textView;

        @Override public View onCreateItemView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            textView = new TextView(parent.getContext());
            textView.setTextColor(Color.GREEN);
            textView.setPadding(20, 60, 20, 60);
            return textView;
        }

        @Override public void bindData(String data, int position, int count) {
            textView.setText(data);
        }
    }

    @Override public boolean isOpenViewState() {
        return true;
    }

    @Override public boolean isTransparentStatusBar() {
        return true;
    }

    @Override public int contentViewBackgroundColor() {
        return Color.WHITE;
    }

    /**
     * 打开左滑关闭Activity功能
     */
    @Override public boolean isOpenSlidingToClose() {
        return true;
    }
}
