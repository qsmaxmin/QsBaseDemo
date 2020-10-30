package com.sugar.grapecollege.common.aspect;

import android.os.Bundle;

import com.qsmaxmin.annotation.aspect.JoinPoint;
import com.qsmaxmin.annotation.aspect.QsIAspect;
import com.qsmaxmin.qsbase.common.log.L;
import com.qsmaxmin.qsbase.common.utils.QsHelper;
import com.sugar.grapecollege.common.model.UserConfig;
import com.sugar.grapecollege.user.LoginActivity;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @CreateBy qsmaxmin
 * @Date 2020/10/26 14:29
 * @Description 自定义方法拦截器-执行登录后再执行原方法
 */
public class LoginAspect implements QsIAspect {
    private static HashMap<Integer, JoinPoint> holder;

    @Override public Object around(JoinPoint point) {
        L.i("LoginAspect", "around........target: " + point.getTarget());
        L.i("LoginAspect", "around........args: " + Arrays.toString(point.getArgs()));
        L.i("LoginAspect", "around........tag: " + point.getTag());

        if (UserConfig.getInstance().isLogin()) {
            Object proceed = point.proceed();
            L.i("LoginAspect", "around(login)........execute original method complete, return value: " + proceed);
            return proceed;
        } else {
            if (holder == null) {
                holder = new HashMap<>();
            }
            int requestCode = 996;
            holder.put(requestCode, point);

            L.i("LoginAspect", "around(no login)........go LoginActivity");
            Bundle bundle = new Bundle();
            bundle.putInt(LoginActivity.BK_REQUEST_CODE, requestCode);
            QsHelper.intent2Activity(LoginActivity.class, bundle);
            return null;
        }
    }

    public static void onLogin(int requestCode) {
        if (holder != null) {
            JoinPoint joinPoint = holder.remove(requestCode);
            if (joinPoint != null) {
                joinPoint.proceed();
                L.i("LoginAspect", "around(no login)........execute original method complete, loss return value !!!!");
            }
        }
    }
}
