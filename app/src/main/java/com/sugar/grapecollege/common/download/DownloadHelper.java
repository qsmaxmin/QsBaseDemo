//package com.sugar.grapecollege.common.download;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Looper;
//import android.provider.Settings;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.SparseArray;
//
//import com.sugar.grapecollege.R;
//import com.sugar.grapecollege.common.download.callback.DownloadCallback;
//import com.sugar.grapecollege.common.download.model.DownloadConstants;
//import com.sugar.grapecollege.common.download.task.DownloadExecutor;
//import com.sugar.grapecollege.common.download.threadpoll.CustomThreadPoll;
//import com.sugar.grapecollege.common.download.threadpoll.DownloadThreadPollManager;
//import com.sugar.grapecollege.common.greendao.DataBaseHelper;
//import com.sugar.grapecollege.common.greendao.model.DownloadState;
//import com.sugar.grapecollege.common.greendao.model.ModelProduct;
//import com.sugar.grapecollege.common.utils.NetworkUtils;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//
//
///**
// * @CreateBy qsmaxmin
// * @Date 2017/4/25 16:50
// * @Description 下载帮助类
// */
//
//public class DownloadHelper {
//
//    private static DownloadHelper                      helper            = new DownloadHelper();
//    /**
//     * 可执行任务的map
//     * key：downloadUrl的hashCode
//     * value：DownloadExecutor
//     */
//    private final  SparseArray<DownloadExecutor>       executorMap       = new SparseArray<>();
//    /**
//     * 全局下载回调，任意字体下载动作都会触发集合中的所有回调
//     */
//    private final  List<DownloadCallback>              globeCallbackList = new ArrayList<>();
//    /**
//     * 字体下载回调
//     * key：productId
//     * value：下载回调DownloadCallback
//     */
//    private final  SparseArray<List<DownloadCallback>> callbackMap       = new SparseArray<>();
//
//    private int                           mThreadCount;
//    private LinkedBlockingQueue<Runnable> mQueue;
//    private OkHttpClient                  mOkHttpClient;
//    private MainCallbackListener          mainListener;
//
//    public static DownloadHelper getInstance() {
//        return helper;
//    }
//
//    private CustomThreadPoll getDownloadThreadPool() {
//        return DownloadThreadPollManager.getInstance().getDownloadService();
//    }
//
//    private CustomThreadPoll getWorkThreadPool() {
//        return DownloadThreadPollManager.getInstance().getWorkService();
//    }
//
//    public void init(Context context, int threadCount) {
//        DataBaseHelper.getInstance().init(context);
//
//        this.mThreadCount = threadCount < 1 ? 1 : threadCount;
//        DownloadThreadPollManager.getInstance().init(threadCount);
//        mQueue = (LinkedBlockingQueue<Runnable>) getDownloadThreadPool().getQueue();
//
//        getWorkThreadPool().execute(new Runnable() {
//            @Override public void run() {
//                recoveryTaskState();
//            }
//        });
//        mOkHttpClient = new OkHttpClient();
//        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
//
//        mainListener = new MainCallbackListener();
//    }
//
//    /**
//     * 恢复数据库异常状态
//     * 如下载中突然结束进程
//     */
//    private void recoveryTaskState() {
//        List<ModelProduct> entities = DataBaseHelper.getInstance().queryAll();
//        ArrayList<ModelProduct> updateList = new ArrayList<>();
//        for (ModelProduct entity : entities) {
//            long completedSize = entity.getTempSize();
//            long totalSize = entity.getSize();
//            if (completedSize > 0 && totalSize > 0 && completedSize < totalSize && entity.getDownloadState() != DownloadState.DOWNLOAD_PAUSE) {
//                entity.setDownloadState(DownloadState.DOWNLOAD_PAUSE);
//                updateList.add(entity);
//            }
//        }
//        DataBaseHelper.getInstance().update(updateList);
//    }
//
//
//    /**
//     * 检查model数据的完整性
//     * 其中{@link ModelProduct#productId, ModelProduct#downloadUrl}为必要数据，不能为空
//     * 当其他数据不完整时，自动补充{@link ModelProduct#productName, ModelProduct#ttfFileName, ModelProduct#zipFileName}
//     */
//    private boolean checkAndInitData(ModelProduct modelProduct) {
//        if (modelProduct == null) {
//            L.e("DownloadHelper  checkAndInitData  modelProduct is null...");
//            return false;
//        } else if (TextUtils.isEmpty(modelProduct.getProductUrl())) {
//            L.e("DownloadHelper  checkAndInitData  downloadUrl is empty...");
//            J2WToast.show("download url is empty");
//            return false;
//        } else if (TextUtils.isEmpty(modelProduct.getProductId())) {
//            L.e("DownloadHelper  checkAndInitData  productId is empty...");
//            J2WToast.show("font id is empty");
//            return false;
//        } else {
//            String productName = modelProduct.getProductName();
//            if (TextUtils.isEmpty(productName)) {
//                productName = getFileNameByUrl(modelProduct.getProductUrl());
//            }
//            if (productName.contains(".")) {
//                productName = productName.split("\\.")[0];
//            }
//
//            modelProduct.setProductName(productName);
//
//            if (TextUtils.isEmpty(modelProduct.getFileName())) {
//                modelProduct.setFileName(productName);
//            }
//
//            if (TextUtils.isEmpty(modelProduct.getPath())) {
//                modelProduct.setPath(DownloadConstants.DEFAULT_ZIP_PATH);
//            }
//            return true;
//        }
//    }
//
//    private String getFileNameByUrl(String url) {
//        String name;
//        if (!TextUtils.isEmpty(url)) {
//            Uri uri = Uri.parse(url);
//            String lastPathSegment = uri.getLastPathSegment();
//            name = TextUtils.isEmpty(lastPathSegment) ? String.valueOf(url.hashCode()) : lastPathSegment;
//        } else {
//            name = String.valueOf(System.currentTimeMillis());
//        }
//        if (name.contains(".")) {
//            String[] split = name.split("\\.");
//            name = split[0];
//        }
//        return name;
//    }
//
//    private void dispatchDownload(final ModelProduct modelProduct, final DownloadCallback callback) {
//        File zipDir = new File(modelProduct.getPath());
//        if (!zipDir.exists() && !zipDir.mkdirs()) {
//            onDownloadFail(modelProduct.getProductId(), "创建文件夹失败...path=" + modelProduct.getPath(), callback);
//            return;
//        }
//        DownloadExecutor executor = executorMap.get(modelProduct.getProductId().hashCode());
//        if (executor == null) {
//            applyNewDownload(modelProduct, callback);
//        } else {
//            applyOldDownload(executor, callback);
//        }
//    }
//
//    private void applyNewDownload(ModelProduct modelProduct, DownloadCallback callback) {
//        DownloadExecutor executor = new DownloadExecutor(modelProduct);
//        executor.setClient(mOkHttpClient);
//        executor.addCallback(callback);//传参回调
//        executor.addCallback(mainListener);//用于全局下载回调
//        executor.addCallbackList(callbackMap.get(modelProduct.getProductId().hashCode()));
//        executorMap.put(modelProduct.getProductId().hashCode(), executor);
//        getDownloadThreadPool().execute(executor);
//        if (getDownloadThreadPool().getTaskCount() > mThreadCount) {
//            executor.applyWait();
//            L.i("DownloadHelper  applyNewDownload....  add wait  fileName=" + modelProduct.getFileName());
//        }
//    }
//
//
//    private void applyOldDownload(DownloadExecutor executor, DownloadCallback callback) {
//        ModelProduct modelProduct = executor.getModelProduct();
//        if (modelProduct != null) {
//            executor.addCallback(callback);
//            executor.addCallback(mainListener);//用于全局下载回调
//            executor.addCallbackList(callbackMap.get(modelProduct.getProductId().hashCode()));
//            if (modelProduct.getDownloadState() != DownloadState.DOWNLOAD_ING) {
//                if (!mQueue.contains(executor)) {
//                    getDownloadThreadPool().execute(executor);
//                }
//                if (getDownloadThreadPool().getTaskCount() > mThreadCount) {
//                    executor.applyWait();
//                    L.i("DownloadHelper  applyOldDownload.... too much task so wait a moment, fileName=" + modelProduct.getFileName());
//                }
//            } else {
//                L.e("DownloadHelper  applyOldDownload.... current download task is running!");
//            }
//        } else {
//            L.e("DownloadHelper  applyOldDownload.... current download task is null!");
//        }
//
//    }
//
//    private void onDownloadFail(final String fontId, final String failMessage, final DownloadCallback callback) {
//        if (!TextUtils.isEmpty(fontId) && callback != null) {
//            if (isMainThread()) {
//                callback.onDownloadFailed(fontId, failMessage);
//            } else {
//                QsHelper.getMainLooper().execute(new Runnable() {
//                    @Override public void run() {
//                        callback.onDownloadFailed(fontId, failMessage);
//                    }
//                });
//            }
//        }
//    }
//
//    private boolean isMainThread() {
//        return Thread.currentThread() == Looper.getMainLooper().getThread();
//    }
//
//    private void rPause(String fontId) {
//        DownloadExecutor executor = executorMap.get(fontId.hashCode());
//        if (mQueue.contains(executor)) {
//            mQueue.remove(executor);
//        }
//        if (executor != null) {
//            executor.applyPause();
//        }
//    }
//
//    private void rCancel(final ModelProduct modelProduct, boolean deleteFile) {
//        int key = modelProduct.getProductId().hashCode();
//        DownloadExecutor executor = executorMap.get(key);
//        if (executor != null) {
//            getDownloadThreadPool().remove(executor);
//            mQueue.remove(executor);
//            executor.applyCancel();
//        } else {
//            DataBaseHelper.getInstance().delete(modelProduct);
//            modelProduct.setDownloadState(DownloadState.DOWNLOAD_INIT);
//            QsHelper.getMainLooper().execute(new Runnable() {
//                @Override public void run() {
//                    synchronized (callbackMap) {
//                        List<DownloadCallback> callbackList = callbackMap.get(modelProduct.getProductId().hashCode());
//                        if (callbackList != null) {
//                            for (DownloadCallback callback : callbackList) {
//                                callback.onDownloadCancel(modelProduct.getProductId());
//                            }
//                        }
//                    }
//                    synchronized (globeCallbackList) {
//                        for (DownloadCallback back : globeCallbackList) {
//                            if (back != null) back.onDownloadCancel(modelProduct.getProductId());
//                        }
//                    }
//                }
//            });
//        }
//        if (deleteFile) {
//            if (!TextUtils.isEmpty(modelProduct.getPath()) && !TextUtils.isEmpty(modelProduct.getFileName())) {
//                File tempFile = new File(modelProduct.getPath(), modelProduct.getFileName());
//                if (tempFile.exists()) {
//                    boolean delete = tempFile.delete();
//                    if (!delete) {
//                        L.e("删除临时文件(" + tempFile.getName() + ")失败！！");
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 检查下载配置信息，4G开关
//     */
//    private void checkDownloadConfig(final Activity activity, final ModelProduct modelProduct, final DownloadCallback callback) {
//        if (NetworkUtils.isNetworkConnected()) {
//            if (!AppConfig.getInstance().forceDownload) {
//                if (!NetworkUtils.isWifiConnected()) {
//                    CompatibleDialog.createBuilder()//
//                            .setPositiveButtonText(QsHelper.getInstance().getString(R.string.download_tips), 14, QsHelper.getInstance().getResources().getColor(R.color.color_gray))//
//                            .setNeutralButtonText(QsHelper.getInstance().getString(R.string.download), QsHelper.getInstance().getResources().getColor(R.color.color_red))//
//                            .setNegativeButtonText(QsHelper.getInstance().getString(R.string.cancel))//
//                            .setOnDialogClickListener(new CompatibleDialog.OnDialogClickListener() {
//                                @Override public void onPositiveButtonClick() {
//                                }
//
//                                @Override public void onNeutralButtonClick() {
//                                    checkPermission(activity, modelProduct, callback);
//                                }
//
//                                @Override public void onNegativeButtonClick() {
//                                }
//                            }).showAllowingStateLoss();
//                } else {
//                    checkPermission(activity, modelProduct, callback);
//                }
//            } else {
//                checkPermission(activity, modelProduct, callback);
//            }
//        } else {
//            CompatibleDialog.createBuilder()//
//                    .setPositiveButtonText(QsHelper.getInstance().getString(R.string.network_disable_tips), 14, QsHelper.getInstance().getResources().getColor(R.color.color_gray))//
//                    .setNeutralButtonText(QsHelper.getInstance().getString(R.string.ok))//
//                    .setNegativeButtonText(QsHelper.getInstance().getString(R.string.cancel), QsHelper.getInstance().getResources().getColor(R.color.color_gray))//
//                    .setOnDialogClickListener(new CompatibleDialog.OnDialogClickListener() {
//                        @Override public void onPositiveButtonClick() {
//                        }
//
//                        @Override public void onNeutralButtonClick() {
//                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            QsHelper.getInstance().startActivity(intent);
//                        }
//
//                        @Override public void onNegativeButtonClick() {
//                        }
//                    }).showAllowingStateLoss();
//        }
//    }
//
//    /**
//     * 检查权限
//     */
//    private void checkPermission(Activity activity, final ModelProduct modelProduct, final DownloadCallback callback) {
//        PermissionUtil.getInstance()//
//                .addWantPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)//
//                .setShowCustomDialog(true)//
//                .setActivity(activity)//
//                .setListener(new PermissionUtil.PermissionListener() {
//                    @Override public void onPermissionCallback(int requestCode, boolean isGrantedAll) {
//                        if (isGrantedAll) {
//                            dispatchDownload(modelProduct, callback);
//                        } else {
//                            onDownloadFail(modelProduct.getProductId(), "SD卡禁止读写~", callback);
//                            L.e("DownloadHelper  requestDownload.... permission not allow");
//                        }
//                    }
//                }).startRequest();
//    }
//
//
//
//
//
//
//    /*------------------------------------------------------------------------------------------------------------------*/
//    /*                                                    华丽的分割线                                                   */
//    /*------------------------------------------------------------------------------------------------------------------*/
//
//    public NormalDownloadTask getNormalDownloadHelper() {
//        return NormalDownloadTask.getInstance();
//    }
//
//    /**
//     * 执行下载动作
//     */
//    public void requestDownload(ModelProduct modelProduct) {
//        requestDownload(modelProduct, null);
//    }
//
//    /**
//     * 下载
//     *
//     * @param modelProduct 下载模型
//     * @param callback     回调
//     */
//    public void requestDownload(final ModelProduct modelProduct, final DownloadCallback callback) {
//        FragmentActivity activity = QsHelper.getScreenHelper().currentActivity();
//        if (activity == null) {
//            L.e("DownloadHelper  requestDownload....  current Activity is null...");
//            return;
//        }
//        if (!checkAndInitData(modelProduct)) return;
//        L.i("DownloadHelper  requestDownload...." + modelProduct.toString());
//        checkDownloadConfig(activity, modelProduct, callback);
//    }
//
//    /**
//     * 暂停下载
//     */
//    public void requestPause(final ModelProduct modelProduct) {
//        if (modelProduct != null) {
//            requestPause(modelProduct.getProductId());
//        }
//    }
//
//    public void requestPause(final String fontId) {
//        if (!TextUtils.isEmpty(fontId)) {
//            L.i("DownloadHelper  requestPause...." + fontId);
//            if (isMainThread()) {
//                getWorkThreadPool().execute(new Runnable() {
//                    @Override public void run() {
//                        rPause(fontId);
//                    }
//                });
//            } else {
//                rPause(fontId);
//            }
//        }
//    }
//
//    public void requestCancel(ModelProduct modelProduct) {
//        requestCancel(modelProduct, true);
//    }
//
//    /**
//     * 取消或删除当前正在下载或下载完成的字体
//     *
//     * @param modelProduct 字体模型
//     * @param deleteFile   是否删除对应的数据库
//     */
//    public void requestCancel(final ModelProduct modelProduct, final boolean deleteFile) {
//        if (modelProduct != null && !TextUtils.isEmpty(modelProduct.getProductId())) {
//            L.i("DownloadHelper  requestCancel...." + modelProduct.toString());
//            if (isMainThread()) {
//                getWorkThreadPool().execute(new Runnable() {
//                    @Override public void run() {
//                        rCancel(modelProduct, deleteFile);
//                    }
//                });
//            } else {
//                rCancel(modelProduct, deleteFile);
//            }
//        }
//    }
//
//    /**
//     * 针对当前字体添加回调，该回调只回调当前字体的下载状态
//     */
//    public void addDownloadObserver(String fontId, DownloadCallback callback) {
//        if (callback != null && !TextUtils.isEmpty(fontId)) {
//            DownloadExecutor executor = executorMap.get(fontId.hashCode());
//            if (executor != null) {
//                executor.addCallback(callback);
//            } else {
//                synchronized (callbackMap) {
//                    List<DownloadCallback> callbackList = callbackMap.get(fontId.hashCode());
//                    if (callbackList == null) {
//                        callbackList = new ArrayList<>();
//                        callbackMap.put(fontId.hashCode(), callbackList);
//                    }
//                    if (!callbackList.contains(callback)) callbackList.add(callback);
//                }
//            }
//        }
//    }
//
//    /**
//     * 添加全局监听
//     */
//    public void addGlobeDownloadObserver(DownloadCallback callback) {
//        if (callback != null) {
//            synchronized (globeCallbackList) {
//                globeCallbackList.add(callback);
//            }
//        }
//    }
//
//    /**
//     * 移除指定回调
//     */
//    public void removeDownloadObserver(DownloadCallback callback) {
//        L.i("DownloadHelper  removeDownloadObserver......callback=" + callback);
//        if (callback != null) {
//            synchronized (callbackMap) {
//                ArrayList<Integer> emptyKeys = new ArrayList<>();
//                for (int i = 0, size = callbackMap.size(); i < size; i++) {
//                    int keyAt = callbackMap.keyAt(i);
//                    List<DownloadCallback> callbackList = callbackMap.get(keyAt);
//                    if (callbackList != null) {
//                        if (callbackList.contains(callback)) callbackList.remove(callback);
//                        if (callbackList.isEmpty()) emptyKeys.add(keyAt);
//                    }
//                }
//                for (Integer key : emptyKeys) {//移除空集合，防止内存泄漏
//                    callbackMap.remove(key);
//                }
//            }
//            for (int i = 0, size = executorMap.size(); i < size; i++) {
//                DownloadExecutor executor = executorMap.get(i);
//                if (executor != null) executor.removeCallback(callback);
//            }
//        }
//    }
//
//    /**
//     * 会移除该fontId下的所有回调
//     */
//    public void removeDownloadObserver(String fontId) {
//        L.i("DownloadHelper  removeDownloadObserver......productId=" + fontId);
//        if (!TextUtils.isEmpty(fontId)) {
//            callbackMap.remove(fontId.hashCode());
//            DownloadExecutor executor = executorMap.get(fontId.hashCode());
//            if (executor != null) executor.removeAllCallback();
//        }
//    }
//
//    /**
//     * 移除指定全局回调
//     */
//    public void removeGlobeDownloadObserver(DownloadCallback callback) {
//        L.i("DownloadHelper  removeGlobeDownloadObserver......");
//        synchronized (globeCallbackList) {
//            globeCallbackList.remove(callback);
//        }
//    }
//
//    /**
//     * 关闭，释放资源
//     */
//    public void shutdown() {
//        executorMap.clear();
//        callbackMap.clear();
//        globeCallbackList.clear();
//        if (mQueue != null) mQueue.clear();
//        mOkHttpClient = null;
//    }
//
//    /**
//     * 每个{@link DownloadExecutor}都会添加一个该回调
//     * 用于监听所有回调事件，使用时可以根据fontId过滤自己需要的下载事件
//     */
//    private class MainCallbackListener implements DownloadCallback {
//        @Override public void onDownloadConnecting(String fontId) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloadConnecting(fontId);
//                }
//            }
//        }
//
//        @Override public void onDownloadStart(String fontId) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloadStart(fontId);
//                }
//            }
//        }
//
//        @Override public void onDownloadPause(String fontId) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloadPause(fontId);
//                }
//            }
//        }
//
//        @Override public void onDownloadRestart(String fontId, int percent) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloadRestart(fontId, percent);
//                }
//            }
//        }
//
//        @Override public void onDownloading(String fontId, int progress) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloading(fontId, progress);
//                }
//            }
//        }
//
//        @Override public void onDownloadComplete(String fontId) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloadComplete(fontId);
//                }
//            }
//        }
//
//        @Override public void onDownloadFailed(String fontId, String message) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloadFailed(fontId, message);
//                }
//            }
//        }
//
//        @Override public void onDownloadWaite(String fontId) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloadWaite(fontId);
//                }
//            }
//        }
//
//        @Override public void onDownloadCancel(String fontId) {
//            synchronized (globeCallbackList) {
//                for (DownloadCallback callback : globeCallbackList) {
//                    if (callback != null) callback.onDownloadCancel(fontId);
//                }
//            }
//        }
//    }
//}
