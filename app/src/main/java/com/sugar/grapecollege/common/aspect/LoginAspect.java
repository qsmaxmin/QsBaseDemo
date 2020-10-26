package com.sugar.grapecollege.common.aspect;

import android.os.Bundle;

import com.qsmaxmin.annotation.aspect.JoinPoint;
import com.qsmaxmin.annotation.aspect.QsIAspect;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.common.model.UserConfig;
import com.sugar.grapecollege.user.LoginActivity;

import java.util.HashMap;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/10/26 14:29
 * @Description 自定义方法拦截器-执行登录后再执行原方法
 */
public class LoginAspect implements QsIAspect {
    private static HashMap<Integer, JoinPoint> holder;

    @Override public Object around(JoinPoint point) {
        if (UserConfig.getInstance().isLogin()) {
            return point.proceed();
        } else {
            if (holder == null) {
                holder = new HashMap<>();
            }
            int requestCode = 996;
            holder.put(requestCode, point);

            Bundle bundle = new Bundle();
            bundle.putInt(LoginActivity.BK_REQUEST_CODE, requestCode);
            QsHelper.intent2Activity(LoginActivity.class, bundle);
            return null;
        }
    }

    public static void onLogin(int requestCode) {
        JoinPoint joinPoint = holder.get(requestCode);
        if (joinPoint != null) {
            joinPoint.proceed();
        }
    }
}
