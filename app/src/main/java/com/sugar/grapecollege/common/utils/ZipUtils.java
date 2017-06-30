package com.sugar.grapecollege.common.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.qsmaxmin.qsbase.common.log.L;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/2 9:33
 * @Description 解压缩工具
 */

public class ZipUtils {

    public static void unZipFile(File src, File des) {
        if (src != null && des != null) {
            if (!src.exists()) {
                L.e("ZipUtils", "ZipUtils  unZipFile src file is not exist...");
                return;
            }
            if (des.exists()) {
                boolean delete = des.delete();
                if (!delete) {
                    L.e("ZipUtils", "ZipUtils  unZipFile delete des file failed...");
                    return;
                }
            }
            if (!des.getParentFile().exists()) {
                boolean mkdirs = des.getParentFile().mkdirs();
                if (!mkdirs) {
                    L.e("ZipUtils", "ZipUtils  unZipFile create target dir fail...");
                    return;
                }
            }
            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;
            ZipFile zipFile = null;
            try {
                zipFile = new ZipFile(src);
                Enumeration<?> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    inputStream = zipFile.getInputStream(entry);
                    fileOutputStream = new FileOutputStream(des);
                    byte[] bytes = new byte[2048];
                    int length;
                    while ((length = inputStream.read(bytes)) > 0) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                StreamCloseUtils.close(fileOutputStream, inputStream, zipFile);
            }
        } else {
            L.e("ZipUtils", "ZipUtils  unZipFile failed des or src file is null...");
        }
    }


    /**
     * Zip 压缩字符串数据
     */
    public static String zipString(String unZipStr) {

        if (TextUtils.isEmpty(unZipStr)) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        ZipOutputStream zip = null;
        try {
            bos = new ByteArrayOutputStream();
            zip = new ZipOutputStream(bos);
            zip.putNextEntry(new ZipEntry("0"));
            zip.write(unZipStr.getBytes());
            zip.closeEntry();
            byte[] encode = bos.toByteArray();
            bos.flush();
            return Base64.encodeToString(encode, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamCloseUtils.close(bos, zip);
        }
        return null;
    }

    /**
     * Zip解压字符串数据
     */
    public static String unZipString(String zipStr) {
        if (TextUtils.isEmpty(zipStr)) {
            return null;
        }
        byte[] t = Base64.decode(zipStr, Base64.DEFAULT);

        ZipInputStream zip = null;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(t);
            zip = new ZipInputStream(in);
            zip.getNextEntry();
            byte[] buffer = new byte[1024];
            int n;
            while ((n = zip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            return out.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamCloseUtils.close(zip, in, out);
        }
        return null;
    }


    /**
     * Gzip压缩字符串数据
     */
    public static String gZipString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        GZIPOutputStream gzip = null;
        try {
            bos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(bos);
            gzip.write(str.getBytes());
            byte[] encode = bos.toByteArray();
            bos.flush();
            return Base64.encodeToString(encode, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamCloseUtils.close(bos, gzip);
        }
        return null;
    }


    /**
     * Gzip解压字符串数据
     */
    public static String unGZipString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        byte[] t = Base64.decode(str, Base64.DEFAULT);
        GZIPInputStream gzip = null;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(t);
            gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[2048];
            int n;
            while ((n = gzip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamCloseUtils.close(gzip, in, out);
        }
        return null;
    }
}
