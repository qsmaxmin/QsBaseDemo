package com.sugar.grapecollege.common.utils;

import com.qsmaxmin.qsbase.common.log.L;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/6 15:49
 * @Description
 */

public class FileUtils {

    public static boolean copyFile(File srcFile, File destFile) {
        if (srcFile.isDirectory() || destFile.isDirectory()) {
            return false;
        }
        if (!destFile.getParentFile().exists()) {
            boolean mkdirs = destFile.getParentFile().mkdirs();
            if (!mkdirs) return false;
        } else {
            if (destFile.exists()) {
                boolean delete = destFile.delete();
                if (delete) L.i("FileUtils", "delete old file=" + destFile.getAbsolutePath());
            }
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            int readLen;
            byte[] buf = new byte[1024 * 4];
            while ((readLen = fis.read(buf)) != -1) {
                fos.write(buf, 0, readLen);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamCloseUtils.close(fos, fis);
        }
        return true;
    }
}
