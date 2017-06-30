//package com.sugar.grapecollege.common.download.task;
//
//import android.Manifest;
//import android.text.TextUtils;
//
//import com.qsmaxmin.qsbase.common.log.L;
//import com.sugar.grapecollege.R;
//
//import java.util.HashMap;
//
//import j2w.team.common.utils.permission.PermissionUtil;
//import j2w.team.modules.download.J2WDownloadListener;
//import j2w.team.modules.toast.J2WToast;
//import j2w.team.mvp.presenter.QsHelper;
//
///**
// * @CreateBy qsmaxmin
// * @Date 16/8/26  下午3:04
// * @Description 下载文件的工具，维护下载队列，识别重复下载，在下载时有通知栏提示，并在下载完成后自动安装
// */
//public class NormalDownloadTask {
//
//    private static NormalDownloadTask normalDownloadTask;
//    private static HashMap<String, NormalDownloadBuilder> downloadInfo = new HashMap<>();
//
//    private NormalDownloadTask() {
//    }
//
//    public static NormalDownloadTask getInstance() {
//        if (normalDownloadTask == null) {
//            synchronized (NormalDownloadTask.class) {
//                if (normalDownloadTask == null) {
//                    normalDownloadTask = new NormalDownloadTask();
//                }
//            }
//        }
//        return normalDownloadTask;
//    }
//
//    public NormalDownloadBuilder createBuilder() {
//        return new NormalDownloadBuilder();
//    }
//
//    void startDownload(final NormalDownloadBuilder mBuild) {
//        PermissionUtil.getInstance()//
//                .addWantPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)//
//                .setActivity(QsHelper.getScreenHelper().currentActivity())//
//                .setListener(new PermissionUtil.PermissionListener() {
//                    @Override public void onPermissionCallback(int requestCode, boolean isAllowAll) {
//                        if (isAllowAll) {
//                            if (!checkCanDownload(mBuild)) {
//                                return;
//                            }
//                            downloadInfo.put(mBuild.getDownloadUrl(), mBuild);
//                            J2WToast.show(QsHelper.getInstance().getString(R.string.start_download));
//                            QsHelper.getDownloader().download(mBuild.getDownloadUrl(), mBuild.getTargetDir().getPath(), mBuild.getFileName(), new J2WDownloadListener() {
//                                @Override public void onDownloadComplete(int id, String keyCode) {
//                                    L.i("NormalDownloadTask","onDownloadComplete  keyCode:" + keyCode);
//                                    if (downloadInfo.containsKey(keyCode)) {
//                                        NormalDownloadBuilder build = downloadInfo.remove(keyCode);
//                                        if (build != null) build.notifyOnDownloadComplete();
//                                    }
//                                }
//
//                                @Override public void onDownloadFailed(int id, String keyCode, int errorCode, String errorMessage) {
//                                    L.i("NormalDownloadTask","onDownloadFailed  keyCode:" + keyCode);
//                                    if (downloadInfo.containsKey(keyCode)) {
//                                        NormalDownloadBuilder build = downloadInfo.remove(keyCode);
//                                        if (build != null) build.notifyDownloadFail(errorMessage);
//                                    }
//                                }
//
//                                @Override public void onDownloadProgress(int id, String keyCode, long totalBytes, long downloadedBytes, int progress) {
//                                    L.i("NormalDownloadTask"," onDownloadProgress   keyCode:" + keyCode + "  progress:" + progress);
//                                    if (downloadInfo.containsKey(keyCode)) {
//                                        NormalDownloadBuilder build = downloadInfo.get(keyCode);
//                                        if (build != null) build.notifyDownloadProgress(progress);
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }).startRequest();
//    }
//
//    private boolean checkCanDownload(NormalDownloadBuilder mBuild) {
//        if (mBuild == null) return false;
//        if (TextUtils.isEmpty(mBuild.getDownloadUrl())) {
//            L.e("NormalDownloadTask","下载链接不能为空");
//            return false;
//        }
//        if (downloadInfo.containsKey(mBuild.getDownloadUrl())) {
//            J2WToast.show(QsHelper.getInstance().getString(R.string.already_download_in));
//            return false;
//        }
//        if (mBuild.getFile() == null) {
//            L.e("NormalDownloadTask","startDownload 必须调用 NormalDownloadBuilder.build()方法");
//            return false;
//        } else if (mBuild.getFile().exists()) {
//            return mBuild.getFile().delete();
//        }
//        return true;
//    }
//
//
//    /**
//     * 下载监听回调
//     */
//    public interface DownloadCallback {
//        void onDownloadProgress(String url, int progress);
//
//        void onDownloadComplete(String url);
//
//        void onDownloadFail(String url, String errorMessage);
//    }
//
//
//}
