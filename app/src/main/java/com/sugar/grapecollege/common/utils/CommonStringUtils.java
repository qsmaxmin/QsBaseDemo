package com.sugar.grapecollege.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.qsmaxmin.qsbase.common.utils.QsHelper;

import java.io.IOException;
import java.io.InputStream;


public class CommonStringUtils {

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     */
    public static String filterEmoji(String source) {
        if (!isContainsEMOJI(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEMOJIChar(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return "";
        } else {
            if (buf.length() == len) {
                return source;
            } else {
                return buf.toString();
            }
        }
    }


    /**
     * 检测是否有表情字符
     */
    private static boolean isContainsEMOJI(String source) {
        if (TextUtils.isEmpty(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEMOJIChar(codePoint)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEMOJIChar(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000));
    }

    /**
     * 转换文件的大小，将文件的字节数转换为kb、mb、或gb
     */
    public static String formatterFileSize(long size) {
        return Formatter.formatFileSize(QsHelper.getInstance().getApplication(), size);
    }

    public static String formatterShortFileSize(long size) {
        return Formatter.formatShortFileSize(QsHelper.getInstance().getApplication(), size);
    }

    /**
     * 读取assets下的txt文件
     *
     * @param fileName 文件的名字
     * @return String 文件的内容
     */
    public static String getStringFromAssets(Context context, String fileName) {
        String string = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            int read = inputStream.read(buffer);
            inputStream.close();
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
}
