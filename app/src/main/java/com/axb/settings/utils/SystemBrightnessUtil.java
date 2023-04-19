package com.axb.settings.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/* loaded from: classes.dex */
public class SystemBrightnessUtil {
    private static int getScreenMode(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "screen_brightness_mode");
        } catch (Exception unused) {
            return 0;
        }
    }

    private static void setScreenMode(int i, Context context) {
        try {
            Settings.System.putInt(context.getContentResolver(), "screen_brightness_mode", i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getSystemBrightness(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setSystemBrightness(int i, Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.System.canWrite(context)) {
                Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return;
            }
            Settings.System.putInt(context.getContentResolver(), "screen_brightness", i);
            return;
        }
        Settings.System.putInt(context.getContentResolver(), "screen_brightness", i);
    }
}
