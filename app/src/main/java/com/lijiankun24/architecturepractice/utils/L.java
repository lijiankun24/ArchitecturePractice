package com.lijiankun24.architecturepractice.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * L.java
 * <p>
 * Created by lijiankun on 17/6/5.
 */

public class L {

    private static String TAG = "lijk";

    public static void initTAG(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        TAG = tag;
    }

    /**
     * log.d
     *
     * @param msg
     */
    public static void d(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.d(TAG, msg);
    }

    /**
     * log.e
     *
     * @param msg
     */
    public static void e(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(TAG, msg);
    }

    /**
     * log.e
     *
     * @param msg
     */
    public static void e(String msg, Throwable throwable) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(TAG, msg, throwable);
    }

    /**
     * log.w
     *
     * @param msg
     */
    public static void w(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.w(TAG, msg);
    }

    /**
     * Log.i
     *
     * @param msg
     */
    public static void i(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.i(TAG, msg);
    }

    /**
     * log.v
     *
     * @param msg
     */
    public static void v(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.v(TAG, msg);
    }
}
