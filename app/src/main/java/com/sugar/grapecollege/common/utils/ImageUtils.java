package com.sugar.grapecollege.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import com.qsmaxmin.qsbase.common.log.L;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @CreateBy qsmaxmin
 * @Date 2016/10/11 10:06
 * @Description
 */
public class ImageUtils {

    public static void bitmapToFile(Bitmap bitmap, File file) {
        bitmapToFile(bitmap, file, true);
    }

    public static void bitmapToFile(Bitmap bitmap, File file, boolean isNoMedia) {
        if (checkSdcardState()) {
            if (file.exists()) {
                boolean delete = file.delete();
                if (!delete) return;
            }
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                boolean isSuccess = parentFile.mkdirs();
                if (!isSuccess) return;
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StreamCloseUtils.close(fos);
            }
            if (isNoMedia) {
                try {
                    File mediaFile = new File(parentFile, ".Nomedia");
                    if (!mediaFile.exists()) {
                        boolean isSuccess = mediaFile.createNewFile();
                        L.i("ImageUtils", "create noMedia " + (isSuccess ? "success" : "fail"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 按比例大小压缩图片（根据路径获取图片并压缩）
     */
    public static Bitmap getBitmapFromFile(String filePath) {
        if (checkSdcardState()) {
            if (!TextUtils.isEmpty(filePath)) {
                return BitmapFactory.decodeFile(filePath);
            }
        }
        return null;
    }

    public static Bitmap getBitmapFromFile(String srcPath, float width, float height) {
        if (TextUtils.isEmpty(srcPath) || width <= 0 || height <= 0 || !checkSdcardState()) return null;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && w > width) {
            be = (int) (newOpts.outWidth / width);
        } else if (w < h && h > height) {
            be = (int) (newOpts.outHeight / height);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;
        return BitmapFactory.decodeFile(srcPath, newOpts);
    }

    private static boolean checkSdcardState() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
}
