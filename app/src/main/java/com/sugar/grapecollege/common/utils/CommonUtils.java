package com.sugar.grapecollege.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import com.qsmaxmin.qsbase.common.utils.QsHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 16:38
 * @Description
 */

public class CommonUtils {

    private static long lastClickTime;

    public static int dp2px(float dp) {
        return (int) dp2px(QsHelper.getInstance().getApplication().getResources(), dp);
    }

    public static int sp2px(float sp) {
        return (int) sp2px(QsHelper.getInstance().getApplication().getResources(), sp);
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * Manifest中meta_data的字符串信息
     */
    public static String getMetaInfo(String metaKey, String defaultValue) {
        PackageManager pManager = QsHelper.getInstance().getApplication().getPackageManager();
        ApplicationInfo appInfo;
        String msg = defaultValue;
        try {
            appInfo = pManager.getApplicationInfo(QsHelper.getInstance().getApplication().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return msg;
        }
        if (appInfo != null && appInfo.metaData != null) {
            if (appInfo.metaData.get(metaKey) != null) {
                Object object = appInfo.metaData.get(metaKey);
                if (object != null) {
                    msg = object.toString();
                }
            }
        }
        return msg;
    }

    public static int getScreenWidth() {
        return getScreenWidth(QsHelper.getInstance().getScreenHelper().currentActivity());
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        if (activity == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        return getScreenHeight(QsHelper.getInstance().getScreenHelper().currentActivity());
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        if (activity == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    /**
     * 是否连击(在View的onClick回调中不断调用该方法来确定是否连击)
     * @return true：连击，false：非连击  0.6秒
     */
    public static boolean isSeriesClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 600) {
            lastClickTime = currentTime;
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    /**
     * 判断是否是数字
     * @return true:是，false:不是
     */
    public static boolean isNumber(String str) {
        return !TextUtils.isEmpty(str) && Pattern.compile("[0-9]*").matcher(str).matches();
    }

    /**
     * 转换文件的大小，将文件的字节数转换为kb、mb、或gb
     */
    public static String formatterFileSize(long size) {
        return Formatter.formatFileSize(QsHelper.getInstance().getApplication(), size);
    }


    /**
     * 获取客户端名称
     */
    public static String getAppVersion() {
        return getAppVersion(QsHelper.getInstance().getApplication().getPackageName());
    }

    public static String getAppVersion(String packageName) {
        String strVersion = "";
        try {
            strVersion = QsHelper.getInstance().getApplication().getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strVersion;
    }

    /**
     * 获取SHA1
     */
    public static String getCertificateSHA1Fingerprint(Context context) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Signature[] signatures = new Signature[0];
        if (packageInfo != null) {
            signatures = packageInfo.signatures;
        }
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        X509Certificate c = null;
        try {
            if (cf != null) {
                c = (X509Certificate) cf.generateCertificate(input);
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            if (c != null) {
                byte[] publicKey = md.digest(c.getEncoded());
                StringBuilder str = new StringBuilder(publicKey.length * 2);
                for (int i = 0; i < publicKey.length; i++) {
                    String h = Integer.toHexString(publicKey[i]);
                    int l = h.length();
                    if (l == 1) h = "0" + h;
                    if (l > 2) h = h.substring(l - 2, l);
                    str.append(h.toUpperCase());
                    if (i < (publicKey.length - 1)) str.append(':');
                }
                hexString = str.toString();
            }
        } catch (NoSuchAlgorithmException | CertificateEncodingException e1) {
            e1.printStackTrace();
        }
        return hexString;
    }

    /**
     * 判断 用户是否安装第三方客户端
     */
    public static boolean isOtherAppAvailable(String packageName) {
        final PackageManager packageManager = QsHelper.getInstance().getApplication().getPackageManager();// 获取packagemanager
        List<PackageInfo> infoList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); i++) {
                String pn = infoList.get(i).packageName;
                if (!TextUtils.isEmpty(pn) && pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sdcard是否可用
     */
    public static boolean isSdcardAvailable() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
}
