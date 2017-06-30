package com.sugar.grapecollege.common.download.task;

import android.os.Looper;

import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.common.download.callback.DownloadCallback;
import com.sugar.grapecollege.common.download.model.CallbackState;
import com.sugar.grapecollege.common.greendao.DataBaseHelper;
import com.sugar.grapecollege.common.greendao.model.DownloadState;
import com.sugar.grapecollege.common.greendao.model.ModelProduct;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 16:51
 * @Description 字体下载引擎，可以执行字体压缩文件的下载，解压，以及下载和字体相关的图片
 */

public class DownloadExecutor implements Runnable {

    private final List<DownloadCallback> callbackList = new ArrayList<>();
    private final Object                 pauseLocker  = new Object();

    private final ModelProduct modelProduct;
    private       OkHttpClient mClient;
    private       boolean      isCancel;

    public DownloadExecutor(ModelProduct modelProduct) {
        this.modelProduct = modelProduct;
    }

    @Override public void run() {
        if (modelProduct == null || mClient == null) {
            return;
        }
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        RandomAccessFile raFile = null;
        try {
            File targetFile = new File(modelProduct.getPath(), modelProduct.getFileName());
            raFile = new RandomAccessFile(targetFile, "rwd");
            ModelProduct queryFont = DataBaseHelper.getInstance().query(modelProduct.getProductId());
            long tempSize;
            boolean isFirstDownload;
            if (queryFont != null) {//数据库有记录
                L.i("DownloadExecutor", "DownloadExecutor  queryFont=" + queryFont.toString());
                if (queryFont.getDownloadState() == DownloadState.DOWNLOAD_COMPLETE && queryFont.getSize() > 0 && targetFile.exists() && targetFile.length() == queryFont.getSize()) {//已下载完成的情况
                    modelProduct.setDownloadState(DownloadState.DOWNLOAD_COMPLETE);
                    modelProduct.setTempSize(queryFont.getTempSize());
                    dispatchCallback(CallbackState.CALLBACK_COMPLETE);
                    L.i("DownloadExecutor", "DownloadExecutor  current font is exist .... " + modelProduct.toString());
                    return;
                } else {
                    if (queryFont.getSize() > 0) {
                        isFirstDownload = false;
                        tempSize = (raFile.length() > 0 && queryFont.getTempSize() > 0) ? queryFont.getTempSize() : 0;
                        modelProduct.setSize(queryFont.getSize());
                        if (tempSize > queryFont.getSize()) {//数据异常处理，删除源文件重新下载
                            L.i("DownloadExecutor", "DownloadExecutor  database data is exception .... " + queryFont.toString());
                            tempSize = 0;
                            isFirstDownload = true;
                            if (targetFile.exists()) {
                                boolean delete = targetFile.delete();
                                if (delete) {
                                    raFile = new RandomAccessFile(targetFile, "rwd");
                                } else {
                                    dispatchCallback(CallbackState.CALLBACK_FAIL, "数据库数据异常，删除源文件失败");
                                    return;
                                }
                            }
                        }
                    } else {
                        isFirstDownload = true;
                        tempSize = 0;
                    }
                    L.i("DownloadExecutor", "DownloadExecutor  tempSize=" + tempSize + "   RandomAccessFile size=" + raFile.length() + "  totalSize=" + queryFont.getSize());
                }
            } else {
                isFirstDownload = true;
                tempSize = 0;
            }
            L.i("DownloadExecutor", "DownloadExecutor  run.... fileName=" + modelProduct.getFileName() + "  tempSize=" + tempSize);
            raFile.seek(tempSize);
            modelProduct.setTempSize(tempSize);

            Request request = new Request.Builder().url(modelProduct.getProductUrl()).header("RANGE", "bytes=" + tempSize + "-").build();

            dispatchCallback(CallbackState.CALLBACK_CONNECTING);
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    long contentLength = responseBody.contentLength();

                    if (isFirstDownload) {
                        modelProduct.setSize(contentLength);
                        modelProduct.setTimeStamp(System.currentTimeMillis());
                        modelProduct.setDownloadState(DownloadState.DOWNLOAD_INIT);
                        DataBaseHelper.getInstance().insertOrReplace(modelProduct);
                    }
                    L.i("DownloadExecutor", "DownloadExecutor  response ok ....  " + modelProduct.toString());

                    dispatchCallback(isFirstDownload ? CallbackState.CALLBACK_START : CallbackState.CALLBACK_RESTART);

                    double stepSize = modelProduct.getSize() / 100;
                    inputStream = responseBody.byteStream();
                    bis = new BufferedInputStream(inputStream);
                    byte[] buffer = new byte[1024 << 2];
                    int length;
                    int buffOffset = 0;
                    modelProduct.setDownloadState(DownloadState.DOWNLOAD_ING);
                    isCancel = false;
                    L.i("DownloadExecutor", "DownloadExecutor  start downloading ....  " + modelProduct.toString());

                    /*这个循环读取流操作时，可能会出现回调不同步现象，其主要原因是在读流时暂停applyPause()方法，该方法通过一个标志位modelFont.getDownloadState()判断是否应该继续循环，
                    * 这样就造成了applyPause方法里的‘暂停’回调和改循环里的‘下载中’回调不同步现象，因下载中回调有个判断条件：(buffOffset >= stepSize)
                    * 假如下载一个4MB的文件，通过计算：4MB/4KB=1024(buffer=4KB)，即while循环1024次，而if (buffOffset >= stepSize) 条件成立100次，所以用户点击暂停时if条件成立
                    * 的概率是1/10，只要if条件成立就有可能if条件里面的回调比applyPause()里面的回调后执行，进而导致数据库数据错乱和回调顺序错误。
                    * 而给if和applyPause加锁，也就是同步执行100次左右，牺牲的性能并不多
                    * 综上所需，还是决定在if里和applyPause里加锁*/
                    while ((length = bis.read(buffer)) > 0 && !isCancel && modelProduct.getDownloadState() != DownloadState.DOWNLOAD_PAUSE) {
                        raFile.write(buffer, 0, length);
                        tempSize += length;
                        buffOffset += length;
                        modelProduct.setTempSize(tempSize);
                        if (buffOffset >= stepSize && modelProduct.getDownloadState() != DownloadState.DOWNLOAD_PAUSE) { //避免一直调用数据库，加锁防止applyPause方法导致下载回调不一致及下载状态错乱
                            synchronized (pauseLocker) {
                                if (modelProduct.getDownloadState() != DownloadState.DOWNLOAD_PAUSE) {
                                    buffOffset = 0;
                                    DataBaseHelper.getInstance().update(modelProduct);
                                    dispatchCallback(CallbackState.CALLBACK_DOWNLOADING);
                                }
                            }
                        }
                        if (tempSize == modelProduct.getSize()) {
                            dispatchCallback(CallbackState.CALLBACK_DOWNLOADING);

                            modelProduct.setDownloadState(DownloadState.DOWNLOAD_COMPLETE);
                            DataBaseHelper.getInstance().update(modelProduct);
                            dispatchCallback(CallbackState.CALLBACK_COMPLETE);
                        }
                    }
                    close(responseBody);
                }
            } else {
                dispatchCallback(CallbackState.CALLBACK_FAIL, response.message());
            }
        } catch (IOException e) {
            dispatchCallback(CallbackState.CALLBACK_FAIL, e.getMessage());
            e.printStackTrace();
        } finally {
            close(bis, inputStream, raFile);
        }
    }


    private void dispatchCallback(final CallbackState state) {
        dispatchCallback(state, "");
    }

    /**
     * 回调
     * @param state   状态
     * @param message 错误message
     */
    private void dispatchCallback(final CallbackState state, final String message) {
        if (modelProduct != null) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                onCallback(state, message);
            } else {
                QsHelper.getInstance().getThreadHelper().getMainThread().execute(new Runnable() {
                    @Override public void run() {
                        onCallback(state, message);
                    }
                });
            }
        }
    }

    private void onCallback(CallbackState state, String message) {
        synchronized (callbackList) {
            if (!callbackList.isEmpty()) {
                switch (state) {
                    case CALLBACK_CONNECTING:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloadConnecting(modelProduct.getProductId());
                        }
                        break;
                    case CALLBACK_START:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloadStart(modelProduct.getProductId());
                        }
                        break;
                    case CALLBACK_DOWNLOADING:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloading(modelProduct.getProductId(), getPercent());
                        }
                        break;
                    case CALLBACK_PAUSE:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloadPause(modelProduct.getProductId());
                        }
                        break;
                    case CALLBACK_COMPLETE:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloadComplete(modelProduct.getProductId());
                        }
                        break;
                    case CALLBACK_CANCEL:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloadCancel(modelProduct.getProductId());
                        }
                        break;
                    case CALLBACK_WAIT:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloadWaite(modelProduct.getProductId());
                        }
                        break;
                    case CALLBACK_FAIL:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloadFailed(modelProduct.getProductId(), message);
                        }
                        break;
                    case CALLBACK_RESTART:
                        for (DownloadCallback callback : callbackList) {
                            if (callback != null) callback.onDownloadRestart(modelProduct.getProductId(), getPercent());
                        }
                        break;
                }
            }
        }
    }

    private void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable io : closeables) {
                if (io != null) {
                    try {
                        io.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private int getPercent() {
        return (modelProduct != null && modelProduct.getSize() > 0) ? (int) (modelProduct.getTempSize() * 100 / modelProduct.getSize()) : 0;
    }

    public ModelProduct getModelProduct() {
        return modelProduct;
    }

    public void setClient(OkHttpClient mOkHttpClient) {
        this.mClient = mOkHttpClient;
    }

    public void addCallback(DownloadCallback callback) {
        if (callback != null) {
            synchronized (callbackList) {
                if (!callbackList.contains(callback)) callbackList.add(callback);
            }
        }
    }

    public void addCallbackList(List<DownloadCallback> list) {
        if (list != null && !list.isEmpty()) {
            synchronized (callbackList) {
                for (DownloadCallback callback : list) {
                    if (!callbackList.contains(callback)) callbackList.add(callback);
                }
            }
        }
    }

    public boolean removeCallback(DownloadCallback callback) {
        synchronized (callbackList) {
            return callbackList.remove(callback);
        }
    }

    public void removeAllCallback() {
        synchronized (callbackList) {
            callbackList.clear();
        }
    }

    public void applyPause() {
        synchronized (pauseLocker) {
            L.i("DownloadExecutor", "DownloadExecutor  applyPause....");
            modelProduct.setDownloadState(DownloadState.DOWNLOAD_PAUSE);
            DataBaseHelper.getInstance().update(modelProduct);
            dispatchCallback(CallbackState.CALLBACK_PAUSE);
        }
    }

    public void applyWait() {
        if (modelProduct.getDownloadState() != DownloadState.DOWNLOAD_COMPLETE) {
            L.i("DownloadExecutor", "DownloadExecutor  applyWait....");
            modelProduct.setDownloadState(DownloadState.DOWNLOAD_WAIT);
            DataBaseHelper.getInstance().insertOrReplace(modelProduct);
            dispatchCallback(CallbackState.CALLBACK_WAIT);
        }
    }

    public void applyCancel() {
        L.i("DownloadExecutor", "DownloadExecutor  applyCancel....");
        this.isCancel = true;
        modelProduct.setDownloadState(DownloadState.DOWNLOAD_INIT);
        DataBaseHelper.getInstance().delete(modelProduct);
        dispatchCallback(CallbackState.CALLBACK_CANCEL);
    }
}
