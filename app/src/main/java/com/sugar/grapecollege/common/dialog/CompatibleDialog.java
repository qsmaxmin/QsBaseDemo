//package com.sugar.grapecollege.common.dialog;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.sugar.grapecollege.R;
//
//import java.io.Serializable;
//
///**
// * @CreateBy qsmaxmin
// * @Date 2017/5/3 15:26
// * @Description 兼容性对话框，可以兼容一至三个按钮的对话框
// * <p>
// * CompatibleDialog.createBuilder()//
// * .setNegativeButtonText("negative")//
// * .setPositiveButtonText("positive")//
// * .setNeutralButtonText("neutral")//
// * .setCancelable(false)//
// * .setOnDialogClickListener(new CompatibleDialog.CompatibleDialogClickListener() {
// * public void onPositiveButtonClick() {
// * J2WToast.show("onPositiveButtonClick");
// * }
// * public void onNeutralButtonClick() {
// * J2WToast.show("onNeutralButtonClick");
// * }
// * public void onNegativeButtonClick() {
// * J2WToast.show("onNegativeButtonClick");
// * }
// * }).showAllowingStateLoss();
// */
//
//public class CompatibleDialog extends J2WDialogFragment {
//
//    protected final static String ARG_POSITIVE_BUTTON_TEXT = "positive_button_text";
//    protected final static String ARG_NEGATIVE_BUTTON_TEXT = "negative_button_text";
//    protected final static String ARG_NEUTRAL_BUTTON_TEXT  = "neutral_button_text";
//
//    protected final static String ARG_POSITIVE_BUTTON_SIZE = "arg_positive_button_size";
//    protected final static String ARG_NEGATIVE_BUTTON_SIZE = "arg_negative_button_size";
//    protected final static String ARG_NEUTRAL_BUTTON_SIZE  = "arg_neutral_button_size";
//
//    protected final static String ARG_POSITIVE_BUTTON_COLOR = "arg_positive_button_color";
//    protected final static String ARG_NEGATIVE_BUTTON_COLOR = "arg_negative_button_color";
//    protected final static String ARG_NEUTRAL_BUTTON_COLOR  = "arg_neutral_button_color";
//
//    protected final static String ARG_LISTENER = "arg_listener";
//
//    public static CompatibleDialogBuilder createBuilder() {
//        return new CompatibleDialogBuilder(CompatibleDialog.class);
//    }
//
//    @Override public int getJ2WStyle() {
//        return R.style.BottomTopDialogStyle;
//    }
//
//    @Override public void onStart() {
//        super.onStart();
//        if (getDialog() == null || getDialog().getWindow() == null) {
//            return;
//        }
//        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//    }
//
//    @Override public void onShow(DialogInterface dialog) {
//    }
//
//    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return build(new OverrideBuild(getContext(), inflater, container)).createView();
//    }
//
//    @Override protected Builder build(Builder b) {
//        OverrideBuild builder = (OverrideBuild) b;
//        final OnDialogClickListener listener = getListener();
//
//        String positiveText = getPositiveButtonText();
//        if (!TextUtils.isEmpty(positiveText)) {
//            builder.setPositiveButton(positiveText, getPositiveButtonSize(), getPositiveButtonColor(), new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    if (listener != null) listener.onPositiveButtonClick();
//                    dismiss();
//                }
//            });
//        }
//
//        String neutralText = getNeutralButtonText();
//        if (!TextUtils.isEmpty(neutralText)) {
//            builder.setNeutralButton(neutralText, getNeutralButtonSize(), getNeutralButtonColor(), new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    if (listener != null) listener.onNeutralButtonClick();
//                    dismiss();
//                }
//            });
//        }
//
//        String negativeText = getNegativeButtonText();
//        if (!TextUtils.isEmpty(negativeText)) {
//            builder.setNegativeButton(negativeText, getNegativeButtonSize(), getNegativeButtonColor(), new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    if (listener != null) listener.onNegativeButtonClick();
//                    dismiss();
//                }
//            });
//        }
//        return builder;
//    }
//
//    private String getPositiveButtonText() {
//        return getArguments().getString(ARG_POSITIVE_BUTTON_TEXT);
//    }
//
//    private String getNeutralButtonText() {
//        return getArguments().getString(ARG_NEUTRAL_BUTTON_TEXT);
//    }
//
//    private String getNegativeButtonText() {
//        return getArguments().getString(ARG_NEGATIVE_BUTTON_TEXT);
//    }
//
//    private float getPositiveButtonSize() {
//        return getArguments().getFloat(ARG_POSITIVE_BUTTON_SIZE);
//    }
//
//    private float getNeutralButtonSize() {
//        return getArguments().getFloat(ARG_NEUTRAL_BUTTON_SIZE);
//    }
//
//    private float getNegativeButtonSize() {
//        return getArguments().getFloat(ARG_NEGATIVE_BUTTON_SIZE);
//    }
//
//    private int getPositiveButtonColor() {
//        return getArguments().getInt(ARG_POSITIVE_BUTTON_COLOR);
//    }
//
//    private int getNeutralButtonColor() {
//        return getArguments().getInt(ARG_NEUTRAL_BUTTON_COLOR);
//    }
//
//    private int getNegativeButtonColor() {
//        return getArguments().getInt(ARG_NEGATIVE_BUTTON_COLOR);
//    }
//
//    private OnDialogClickListener getListener() {
//        return (OnDialogClickListener) getArguments().getSerializable(ARG_LISTENER);
//    }
//
//    private class OverrideBuild extends Builder {
//
//        private CharSequence         mPositiveStr;
//        private CharSequence         mNegativeStr;
//        private CharSequence         mNeutralStr;
//        private float                mPositiveSize;
//        private float                mNegativeSize;
//        private float                mNeutralSize;
//        private int                  mPositiveColor;
//        private int                  mNegativeColor;
//        private int                  mNeutralColor;
//        private View.OnClickListener mPositiveListener;
//        private View.OnClickListener mNegativeListener;
//        private View.OnClickListener mNeutralListener;
//
//        private OverrideBuild(Context context, LayoutInflater inflater, ViewGroup container) {
//            super(context, inflater, container);
//        }
//
//        @Override public View createView() {
//            View content = View.inflate(getContext(), R.layout.dialog_compatible, null);
//            TextView tv_positive = (TextView) content.findViewById(R.id.tv_positive);
//            TextView tv_neutral = (TextView) content.findViewById(R.id.tv_neutral);
//            TextView tv_negative = (TextView) content.findViewById(R.id.tv_negative);
//            View view_line = content.findViewById(R.id.view_line);
//
//            View view_click = content.findViewById(R.id.view_click);
//            if (mNegativeListener != null && isCancelable()) view_click.setOnClickListener(mNegativeListener);
//
//            set(tv_negative, mNegativeStr, mNegativeSize, mNegativeColor, mNegativeListener);
//            set(tv_positive, mPositiveStr, mPositiveSize, mPositiveColor, mPositiveListener);
//            set(tv_neutral, mNeutralStr, mNeutralSize, mNeutralColor, mNeutralListener);
//
//            if (TextUtils.isEmpty(mPositiveStr) || TextUtils.isEmpty(mNeutralStr)) {
//                view_line.setVisibility(View.GONE);
//                if (!TextUtils.isEmpty(mPositiveStr)) {
//                    tv_positive.setBackgroundResource(R.drawable.selector_bg_white_gray_5radius);
//                }
//                if (!TextUtils.isEmpty(mNeutralStr)) {
//                    tv_neutral.setBackgroundResource(R.drawable.selector_bg_white_gray_5radius);
//                }
//            } else {
//                view_line.setVisibility(View.VISIBLE);
//            }
//            return content;
//        }
//
//
//        private void set(TextView textView, CharSequence text, float textSize, int color, View.OnClickListener listener) {
//            if (text != null) {
//                textView.setText(text);
//                textView.setTextSize(textSize);
//                textView.setTextColor(color);
//            } else {
//                textView.setVisibility(View.GONE);
//            }
//            if (listener != null) {
//                textView.setOnClickListener(listener);
//            }
//        }
//
//        Builder setPositiveButton(CharSequence text, float textSize, int textColor, View.OnClickListener listener) {
//            this.mPositiveStr = text;
//            this.mPositiveSize = textSize;
//            this.mPositiveColor = textColor;
//            this.mPositiveListener = listener;
//            return this;
//        }
//
//        Builder setNegativeButton(CharSequence text, float textSize, int textColor, View.OnClickListener listener) {
//            this.mNegativeStr = text;
//            this.mNegativeSize = textSize;
//            this.mNegativeColor = textColor;
//            this.mNegativeListener = listener;
//            return this;
//        }
//
//        Builder setNeutralButton(CharSequence text, float textSize, int textColor, View.OnClickListener listener) {
//            this.mNeutralStr = text;
//            this.mNeutralSize = textSize;
//            this.mNeutralListener = listener;
//            this.mNeutralColor = textColor;
//            return this;
//        }
//    }
//
//
//    public static class CompatibleDialogBuilder extends J2WDialogBuilder<CompatibleDialogBuilder> {
//        private float defaultTextSize = 15f;
//        private String                mPositiveButtonText;
//        private String                mNegativeButtonText;
//        private String                mNeutralButtonText;
//        private float                 mPositiveSize;
//        private float                 mNegativeSize;
//        private float                 mNeutralSize;
//        private int                   mPositiveColor;
//        private int                   mNegativeColor;
//        private int                   mNeutralColor;
//        private OnDialogClickListener mListener;
//        private int                   defaultColor;
//
//        private CompatibleDialogBuilder(Class<? extends CompatibleDialog> clazz) {
//            super(clazz);
//            defaultColor = mContext.getResources().getColor(R.color.color_accent);
//        }
//
//        @Override protected CompatibleDialogBuilder self() {
//            return this;
//        }
//
//        public CompatibleDialogBuilder setPositiveButtonText(String text) {
//            return setPositiveButtonText(text, defaultTextSize, defaultColor);
//        }
//
//        public CompatibleDialogBuilder setPositiveButtonText(String text, float textSize) {
//            return setPositiveButtonText(text, textSize, defaultColor);
//        }
//
//        public CompatibleDialogBuilder setPositiveButtonText(String text, int textColor) {
//            return setPositiveButtonText(text, defaultTextSize, textColor);
//        }
//
//        public CompatibleDialogBuilder setPositiveButtonText(String text, float textSize, int textColor) {
//            this.mPositiveButtonText = text;
//            this.mPositiveSize = textSize;
//            this.mPositiveColor = textColor;
//            return this;
//        }
//
//
//        public CompatibleDialogBuilder setNeutralButtonText(String text) {
//            return setNeutralButtonText(text, defaultTextSize, defaultColor);
//        }
//
//        public CompatibleDialogBuilder setNeutralButtonText(String text, float textSize) {
//            return setNeutralButtonText(text, textSize, defaultColor);
//        }
//
//        public CompatibleDialogBuilder setNeutralButtonText(String text, int textColor) {
//            return setNeutralButtonText(text, defaultTextSize, textColor);
//        }
//
//        public CompatibleDialogBuilder setNeutralButtonText(String text, float textSize, int textColor) {
//            this.mNeutralButtonText = text;
//            this.mNeutralSize = textSize;
//            this.mNeutralColor = textColor;
//            return this;
//        }
//
//
//        public CompatibleDialogBuilder setNegativeButtonText(String text) {
//            return setNegativeButtonText(text, defaultTextSize, defaultColor);
//        }
//
//        public CompatibleDialogBuilder setNegativeButtonText(String text, float textSize) {
//            return setNegativeButtonText(text, textSize, defaultColor);
//        }
//
//        public CompatibleDialogBuilder setNegativeButtonText(String text, int textColor) {
//            return setNegativeButtonText(text, defaultTextSize, textColor);
//        }
//
//        public CompatibleDialogBuilder setNegativeButtonText(String text, float textSize, int textColor) {
//            this.mNegativeButtonText = text;
//            this.mNegativeSize = textSize;
//            this.mNegativeColor = textColor;
//            return this;
//        }
//
//        public CompatibleDialogBuilder setOnDialogClickListener(OnDialogClickListener listener) {
//            mListener = listener;
//            return this;
//        }
//
//        @Override protected Bundle prepareArguments() {
//            L.i("prepareArguments()");
//            Bundle args = new Bundle();
//            args.putString(CompatibleDialog.ARG_POSITIVE_BUTTON_TEXT, mPositiveButtonText);
//            args.putString(CompatibleDialog.ARG_NEGATIVE_BUTTON_TEXT, mNegativeButtonText);
//            args.putString(CompatibleDialog.ARG_NEUTRAL_BUTTON_TEXT, mNeutralButtonText);
//
//            args.putFloat(CompatibleDialog.ARG_POSITIVE_BUTTON_SIZE, mPositiveSize);
//            args.putFloat(CompatibleDialog.ARG_NEGATIVE_BUTTON_SIZE, mNegativeSize);
//            args.putFloat(CompatibleDialog.ARG_NEUTRAL_BUTTON_SIZE, mNeutralSize);
//
//            args.putInt(CompatibleDialog.ARG_POSITIVE_BUTTON_COLOR, mPositiveColor);
//            args.putInt(CompatibleDialog.ARG_NEGATIVE_BUTTON_COLOR, mNegativeColor);
//            args.putInt(CompatibleDialog.ARG_NEUTRAL_BUTTON_COLOR, mNeutralColor);
//            args.putSerializable(CompatibleDialog.ARG_LISTENER, mListener);
//            return args;
//        }
//    }
//
//    public interface OnDialogClickListener extends Serializable {
//        void onPositiveButtonClick();
//
//        void onNeutralButtonClick();
//
//        void onNegativeButtonClick();
//    }
//}
