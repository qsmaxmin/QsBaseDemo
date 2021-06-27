package com.sugar.grapecollege.common.widget.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/6/25 15:20
 * @Description
 */
public class SettingView {
    private final PopupWindow                 popupWindow;
    private final String[]                    speedTextArr;
    private final float[]                     speedArr;
    private final int[]                       speedButtonIdArr;
    private final int                         DEFAULT_INDEX = 3;
    private       OnSpeedCheckChangedListener speedCheckChangedListener;
    private final RadioGroup                  rg_speed;

    @SuppressLint("InflateParams")
    public SettingView(Context context) {
        speedTextArr = new String[]{"0.25x", "0.5x", "0.75x", "正常", "1.25x", "1.5x", "2x"};
        speedArr = new float[]{0.25f, 0.5f, .75f, 1f, 1.25f, 1.5f, 2f};
        speedButtonIdArr = new int[]{R.id.rb_0, R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4, R.id.rb_5, R.id.rb_6};

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_video_controller_setting, null);
        rg_speed = view.findViewById(R.id.rg_speed);
        for (int i = 0; i < speedButtonIdArr.length; i++) {
            RadioButton button = rg_speed.findViewById(speedButtonIdArr[i]);
            button.setText(speedTextArr[i]);
        }
        rg_speed.setOnCheckedChangeListener((group, checkedId) -> {
            int index = findIndexById(checkedId);
            float speed = speedArr[index];
            dismiss();
            if (speedCheckChangedListener != null) {
                speedCheckChangedListener.onSpeedCheckChanged(speed);
            }
        });

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (Util.SDK_INT < 23) {
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        popupWindow.setOutsideTouchable(true);
    }

    private int findIndexById(int id) {
        for (int i = 0; i < speedButtonIdArr.length; i++) {
            if (speedButtonIdArr[i] == id) return i;
        }
        return speedButtonIdArr[DEFAULT_INDEX];
    }

    private int findIdBySpeed(float speed) {
        for (int i = 0; i < speedArr.length; i++) {
            if (speedArr[i] == speed) return speedButtonIdArr[i];
        }
        return speedButtonIdArr[DEFAULT_INDEX];
    }

    void setOnSpeedCheckChangedListener(OnSpeedCheckChangedListener listener) {
        this.speedCheckChangedListener = listener;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener dismissListener) {
        popupWindow.setOnDismissListener(dismissListener);
    }

    public void updateSelectedIndex(float speed) {
        int buttonId = findIdBySpeed(speed);
        rg_speed.check(buttonId);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    interface OnSpeedCheckChangedListener {
        void onSpeedCheckChanged(float speed);
    }

    void showAtTop(View anchor) {
        popupWindow.showAtLocation(anchor, Gravity.END | Gravity.BOTTOM, 0, 0);
    }
}
