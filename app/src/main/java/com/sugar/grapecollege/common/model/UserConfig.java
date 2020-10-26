package com.sugar.grapecollege.common.model;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.qsmaxmin.annotation.properties.AutoProperty;
import com.qsmaxmin.annotation.properties.Property;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.qsmaxmin.qsbase.plugin.property.QsProperties;


/**
 * @CreateBy qsmaxmin
 * @Date 16/7/29
 * @Description 用户配置信息序列化
 */
@AutoProperty
public class UserConfig extends QsProperties {
    private volatile static UserConfig USER_CONFIG;

    private UserConfig(String configName) {
        super(configName);
    }

    @Override public String initTag() {
        return "UserConfig";
    }

    private static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static UserConfig getInstance() {
        if (USER_CONFIG == null) {
            synchronized (UserConfig.class) {
                if (USER_CONFIG == null) {
                    String androidId = getAndroidId(QsHelper.getApplication());
                    USER_CONFIG = new UserConfig("UserConfig_" + androidId);
                    USER_CONFIG.userId = androidId;
                }
            }
        }
        return USER_CONFIG;
    }

    @Property public String userId;
    @Property public String userName;

    public static void login(String userName) {
        getInstance().userName = userName;
        getInstance().commit();
    }

    public static void logout() {
        if (USER_CONFIG != null) {
            USER_CONFIG.userName = null;
            USER_CONFIG.commit();
            USER_CONFIG = null;
        }
    }

    public boolean isLogin() {
        return (!TextUtils.isEmpty(userName));
    }
}
