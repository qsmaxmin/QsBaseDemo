//package com.sugar.grapecollege.common.download.task;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Environment;
//import android.support.annotation.NonNull;
//import android.support.v7.app.NotificationCompat;
//import android.text.TextUtils;
//
//import com.sugar.grapecollege.R;
//import com.sugar.grapecollege.common.model.AppConstants;
//
//import java.io.File;
//import java.util.Random;
//
//import com.qsmaxmin.qsbase.common.log.L;
//import j2w.team.common.utils.AppUtils;
//import j2w.team.modules.toast.J2WToast;
//import j2w.team.mvp.presenter.QsHelper;
//
///**
// * @CreateBy qsmaxmin
// * @Date 2017/2/17 16:03
// * @Description
// */
//
//public class NormalDownloadBuilder {
//    /*可赋值变量*/
//    private String                              mFileName;
//    private String                              mMimeType;
//    private NormalDownloadTask.DownloadCallback mCallback;
//    private boolean                             mShowInStatus;
//    private boolean                             mIsAutoOpen;
//    private String                              mUrl;
//    private NotificationCompat.Builder          mBuilder;
//    private File                                file;
//    private File                                dir;
//
//    private        int                 currentProgress      = 0;
//    private        String              DOWNLOAD_PATH        = AppConstants.PATH_DOWNLOAD;
//    private static NotificationManager mNotificationManager = (NotificationManager) QsHelper.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
//    private        int                 RANDOM_ID            = new Random().nextInt();
//
//    NormalDownloadBuilder() {
//    }
//
//    void notifyOnDownloadComplete() {
//        if (mShowInStatus) {
//            initNotification();
//            mBuilder.setProgress(100, 100, false);
//            mBuilder.setContentIntent(getCompleteIntent(file, PendingIntent.FLAG_CANCEL_CURRENT)); //设置通知栏点击意图
//            mBuilder.setAutoCancel(true);
//            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合。Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
//            mBuilder.setContentTitle(QsHelper.getInstance().getString(R.string.click_open, mFileName));
//            mNotificationManager.notify(RANDOM_ID, mBuilder.build());
//        }
//        if (mCallback != null) {
//            mCallback.onDownloadComplete(mUrl);
//        }
//        if (mIsAutoOpen) {
//            Intent intent = getIntent(file);
//            QsHelper.getInstance().startActivity(intent);
//        } else {
//            J2WToast.show(QsHelper.getInstance().getString(R.string.download_success));
//        }
//    }
//
//    void notifyDownloadProgress(int progress) {
//        if (mShowInStatus) {
//            initNotification();
//            if (progress % 2 == 0 && currentProgress != progress) {
//                mBuilder.setProgress(100, progress, false);
//                currentProgress = progress;
//                mNotificationManager.notify(RANDOM_ID, mBuilder.build());
//            }
//        }
//        if (mCallback != null) {
//            mCallback.onDownloadProgress(mUrl, progress);
//        }
//    }
//
//    void notifyDownloadFail(String errorMessage) {
//        if (mShowInStatus) {
//            initNotification();
//            mBuilder.setAutoCancel(true);
//            mBuilder.setContentTitle(QsHelper.getInstance().getString(R.string.download_fail));
//            mNotificationManager.notify(RANDOM_ID, mBuilder.build());
//        }
//        if (mCallback != null) {
//            mCallback.onDownloadFail(mUrl, errorMessage);
//        } else {
//            J2WToast.show(QsHelper.getInstance().getString(R.string.download_fail));
//        }
//    }
//
//    public NormalDownloadBuilder setDownloadUrl(String url) {
//        this.mUrl = url;
//        return this;
//    }
//
//    public String getDownloadUrl() {
//        return mUrl;
//    }
//
//    public NormalDownloadBuilder setFileName(String mFileName) {
//        this.mFileName = mFileName;
//        return this;
//    }
//
//    public File getFile() {
//        return file;
//    }
//
//    public NormalDownloadBuilder setMimeType(String mMimeType) {
//        this.mMimeType = mMimeType;
//        return this;
//    }
//
//    public NormalDownloadBuilder setCallback(NormalDownloadTask.DownloadCallback mCallback) {
//        this.mCallback = mCallback;
//        return this;
//    }
//
//    /**
//     * 是否在状态栏展示进度，默认不展示
//     */
//    public NormalDownloadBuilder setShowInStatus(boolean mShowInStatus) {
//        this.mShowInStatus = mShowInStatus;
//        return this;
//    }
//
//    /**
//     * 设置下载目录，若不设置默认{@link AppConstants#PATH_DOWNLOAD}
//     */
//    public NormalDownloadBuilder setDownloadDirPath(String downloadPath) {
//        this.DOWNLOAD_PATH = downloadPath;
//        return this;
//    }
//
//    /**
//     * 下载完成是否自动打开
//     */
//    public NormalDownloadBuilder setAutoOpen(boolean isAutoOpen) {
//        this.mIsAutoOpen = isAutoOpen;
//        return this;
//    }
//
//    public File getTargetDir() {
//        return dir;
//    }
//
//    public String getFileName() {
//        return mFileName;
//    }
//
//    public String getMimeType() {
//        return mMimeType;
//    }
//
//    public NormalDownloadTask.DownloadCallback getCallback() {
//        return mCallback;
//    }
//
//    public boolean isShowInStatus() {
//        return mShowInStatus;
//    }
//
//    public String getDownloadPath() {
//        return DOWNLOAD_PATH;
//    }
//
//    public boolean getIsAutoOpen() {
//        return mIsAutoOpen;
//    }
//
//    public int getCurrentProgress() {
//        return currentProgress;
//    }
//
//    public void startDownload() {
//        L.i("startDownload url：" + mUrl);
//        if (TextUtils.isEmpty(mUrl)) {
//            return;
//        }
//        this.mFileName = TextUtils.isEmpty(mFileName) ? getRealFileName(mUrl) : mFileName;
//        this.mMimeType = getRealMIMEType(mMimeType);
//        if (AppUtils.isSDCardAvailable()) {
//            dir = new File(Environment.getExternalStorageDirectory(), DOWNLOAD_PATH);
//        } else {
//            dir = new File(QsHelper.getInstance().getApplicationContext().getCacheDir(), DOWNLOAD_PATH);
//        }
//        if (!dir.exists()) {
//            if (!dir.mkdirs()) {
//                J2WToast.show(QsHelper.getInstance().getString(R.string.make_dir_fail));
//                return;
//            }
//        }
//        file = new File(dir, mFileName);
//        NormalDownloadTask.getInstance().startDownload(this);
//    }
//
//
//    private void initNotification() {
//        if (mBuilder == null) {
//            mBuilder = new NotificationCompat.Builder(QsHelper.getInstance());
//            mBuilder.setContentTitle(mFileName)//设置通知栏标题
//                    .setTicker(QsHelper.getInstance().getString(R.string.downloading)) //通知首次出现在通知栏，带上升动画效果的
//                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//                    .setOngoing(true)//ture，设置他为一个正在进行的通知，如播放音乐，如一个文件下载
//                    .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
//            mNotificationManager.notify(RANDOM_ID, mBuilder.build());
//        }
//    }
//
//    private String getRealFileName(String url) {
//        if (TextUtils.isEmpty(url)) return String.valueOf(new Random().nextLong());
//        Uri uri = Uri.parse(url);
//        String path = uri.getPath();
//        if (!TextUtils.isEmpty(path) && path.contains("/")) {
//            return path.substring(path.lastIndexOf("/") + 1, path.length());
//        } else {
//            return path;
//        }
//    }
//
//    private PendingIntent getCompleteIntent(File file, int flags) {
//        Intent intent = getIntent(file);
//        return PendingIntent.getActivity(QsHelper.getInstance(), 1, intent, flags);
//    }
//
//    @NonNull private Intent getIntent(File file) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = getUri(intent, file);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(uri, mMimeType);
//        return intent;
//    }
//
//    private Uri getUri(Intent intent, File file) {
//        return Uri.fromFile(file);
//    }
//
//    private String getRealMIMEType(String mimeType) {
//        if (!TextUtils.isEmpty(mimeType)) {
//            return mimeType;
//        }
//        String type;
//            /* 取得扩展名 */
//        String end;
//        if (TextUtils.isEmpty(mFileName) || !mFileName.contains(".")) {
//            end = "";
//        } else {
//            end = mFileName.substring(mFileName.lastIndexOf(".") + 1, mFileName.length()).toLowerCase();
//        }
//            /* 依扩展名的类型决定MimeType */
//        switch (end) {
//            case "pdf":
//                type = "application/pdf";//
//                break;
//
//            case "m4a":
//            case "mp3":
//            case "mid":
//            case "xmf":
//            case "ogg":
//            case "wav":
//                type = "audio/*";
//                break;
//
//            case "3gp":
//            case "mp4":
//                type = "video/*";
//                break;
//
//            case "jpg":
//            case "gif":
//            case "png":
//            case "jpeg":
//            case "bmp":
//                type = "image/*";
//                break;
//
//            case "apk":
//                type = "application/vnd.android.package-archive";
//                break;
//
//            default:
//                type = "*/*";
//                break;
//        }
//        return type;
//    }
//}
