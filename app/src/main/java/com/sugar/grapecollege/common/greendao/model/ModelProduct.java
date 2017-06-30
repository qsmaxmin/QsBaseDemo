package com.sugar.grapecollege.common.greendao.model;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 11:28
 * @Description
 */

@Entity(nameInDb = "FontTab")
public class ModelProduct {
    @Id @Index(unique = true) private String productId;
    private                           String productName;
    private                           String productUrl;
    private                           String imageUrl;
    private                           long   size;
    private                           long   tempSize;
    private                           String desc;
    private                           String author;
    private                           String fileName;
    private                           String path;
    private                           long   timeStamp;

    @Convert(converter = DownloadStateConverter.class, columnType = String.class) private DownloadState downloadState;

    @Generated(hash = 1504807614) public ModelProduct(String productId, String productName, String productUrl, String imageUrl, long size, long tempSize, String desc, String author, String fileName, String path, long timeStamp, DownloadState downloadState) {
        this.productId = productId;
        this.productName = productName;
        this.productUrl = productUrl;
        this.imageUrl = imageUrl;
        this.size = size;
        this.tempSize = tempSize;
        this.desc = desc;
        this.author = author;
        this.fileName = fileName;
        this.path = path;
        this.timeStamp = timeStamp;
        this.downloadState = downloadState;
    }

    @Generated(hash = 1292725854) public ModelProduct() {
    }

    @Override public boolean equals(Object obj) {
        return obj != null && obj instanceof ModelProduct && !TextUtils.isEmpty(productId) && productId.equals(((ModelProduct) obj).getProductId());
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return this.productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTempSize() {
        return this.tempSize;
    }

    public void setTempSize(long tempSize) {
        this.tempSize = tempSize;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public DownloadState getDownloadState() {
        return this.downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }
}
