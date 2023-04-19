package com.axb.settings.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/* loaded from: classes.dex */
public class SharePrefUtils {
    private static SharedPreferences mSharePref;

    private View mLayout;
    private boolean isAddLayout;
    private WindowManager mWindowManager;
    private static SharePrefUtils mLockColor;

    @SuppressLint("WrongConstant")
    private SharePrefUtils(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //WindowManager.LayoutParams params = new WindowManager.LayoutParams(-1, -1, 2006, 1032, -3);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.flags |= 201326592;

        mLayout = new LinearLayout(context);
        mWindowManager.addView(mLayout, params);
        isAddLayout = true;
    }

    public static SharePrefUtils instance(Context context) {
        if (mLockColor == null) {
            mLockColor = new SharePrefUtils(context);
        }
        return mLockColor;
    }

    /**
     * 移除悬浮按钮所在的布局
     */
    public void removeLayout() {
        if (isAddLayout) {
            mWindowManager.removeView(mLayout);
            isAddLayout = false;
            mLockColor = null;
        }
    }

    /**
     * 过滤蓝光
     *
     * @param blueFilterPercent 蓝光过滤比例[10-80]
     */
    public void changeColor(int blueFilterPercent) {
        int realFilter = blueFilterPercent;
        if (realFilter < 30) {
            realFilter = 30;
        } else if (realFilter > 60) {
            realFilter = 60;
        }
        int a = (int) (realFilter / 80f * 120);
        int r = (int) (255 - (realFilter / 80f) * 120);
        int g = (int) (90 - (realFilter / 80f) * 90);
        int b = (int) (255 - realFilter / 80f * 120);

        mLayout.setBackgroundColor(Color.argb(a, r, g, b));
    }


    public static void init(Context context, String str) {
        mSharePref = context.getSharedPreferences(str, 0);
    }

    public static void clearAll() {
        mSharePref.edit().clear().apply();
        mSharePref.edit().apply();
    }

    public static void clearNamePref(Context context, String str) {
        context.getSharedPreferences(str, 0).edit().clear().apply();
        context.getSharedPreferences(str, 0).edit().apply();
    }

    public static void putString(String str, String str2) {
        SharedPreferences.Editor edit = mSharePref.edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getString(String str, String str2) {
        return mSharePref.getString(str, str2);
    }

    public static void putInt(String str, int i) {
        SharedPreferences.Editor edit = mSharePref.edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void putInt(int i, int i2) {
        SharedPreferences.Editor edit = mSharePref.edit();
        edit.putInt(String.valueOf(i), i2);
        edit.apply();
    }

    public static int getInt(String str, int i) {
        return mSharePref.getInt(str, i);
    }

    public static int getInt(int i, int i2) {
        return mSharePref.getInt(String.valueOf(i), i2);
    }

    public static void putLong(String str, long j) {
        SharedPreferences.Editor edit = mSharePref.edit();
        edit.putLong(str, j);
        edit.apply();
    }

    public static long getLong(String str, long j) {
        return mSharePref.getLong(str, j);
    }

    public static void putBoolean(String str, boolean z) {
        SharedPreferences.Editor edit = mSharePref.edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static void putBoolean(int i, boolean z) {
        SharedPreferences.Editor edit = mSharePref.edit();
        edit.putBoolean(String.valueOf(i), z);
        edit.apply();
    }

    public static boolean getBoolean(String str, boolean z) {
        return mSharePref.getBoolean(str, z);
    }

    public static boolean getBoolean(int i, boolean z) {
        return mSharePref.getBoolean(String.valueOf(i), z);
    }
}
