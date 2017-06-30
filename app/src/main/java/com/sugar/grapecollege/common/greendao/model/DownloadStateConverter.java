package com.sugar.grapecollege.common.greendao.model;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 12:13
 * @Description
 */

 class DownloadStateConverter implements PropertyConverter<DownloadState, String> {

    @Override public DownloadState convertToEntityProperty(String databaseValue) {
        return DownloadState.valueOf(databaseValue);
    }

    @Override public String convertToDatabaseValue(DownloadState entityProperty) {
        return entityProperty.name();
    }
}
