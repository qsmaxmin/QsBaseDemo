package com.sugar.grapecollege.common.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsmaxmin.qsbase.common.widget.dialog.QsDialogFragment;
import com.sugar.grapecollege.R;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/7/3 18:29
 * @Description
 */

public class CustomDialog extends QsDialogFragment implements View.OnClickListener {

    @Override protected int getDialogTheme() {
        return R.style.BottomTopDialogStyle;
    }

    @Override protected View getDialogView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.dialog_compatible, viewGroup);
        setView(view);
        return view;
    }

    private void setView(View view) {
        View view_click = view.findViewById(R.id.view_click);
        TextView tv_positive = (TextView) view.findViewById(R.id.tv_positive);
        TextView tv_neutral = (TextView) view.findViewById(R.id.tv_neutral);
        TextView tv_negative = (TextView) view.findViewById(R.id.tv_negative);

        view_click.setOnClickListener(this);
        tv_positive.setOnClickListener(this);
        tv_neutral.setOnClickListener(this);
        tv_negative.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_click:
                break;
            case R.id.tv_positive:
                break;
            case R.id.tv_neutral:
                break;
            case R.id.tv_negative:
                break;
        }
        dismissAllowingStateLoss();
    }
}
