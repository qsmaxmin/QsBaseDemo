package com.sugar.grapecollege.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/2 10:01
 * @Description 关流神器
 */

public class StreamCloseUtils {

    public static void close(Closeable... closeable) {
        if (closeable != null) {
            for (Closeable able : closeable) {
                if (able != null) {
                    try {
                        able.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
