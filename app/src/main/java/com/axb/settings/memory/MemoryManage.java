package com.axb.settings.memory;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.VisibleForTesting;

public class MemoryManage {

    private static final double SZ_1G = 1 * 1000 * 1000 * 1000L;
    private static final double SZ_512M = 500 * 1000 * 1000L;
    private static final String TAG = "MemoryManage";
    private static final long INVALID_MEM_SIZE = -1L;
    private long mTotalMem = INVALID_MEM_SIZE;
    private long mAvailMem = INVALID_MEM_SIZE;

    public MemoryManage(Context context) {
    }

    //获取总的内存
    private long getSystemTotalMemory(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }

        Log.d(TAG, "getSystemTotalMemory: " + memoryInfo.totalMem);


        return covertUnitsToSI(memoryInfo.totalMem);
    }

    private long covertUnitsToSI(long size) {
        double leftRam = size % SZ_1G;
        if ((leftRam) != 0) {
            if (leftRam >= SZ_512M) {
                size += SZ_1G - leftRam;

            } else {
                size -= leftRam;
            }
        }
        return size;
    }

    //获取可用内存
    private long getSystemAvailMemory(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }
        return memoryInfo.availMem;
    }

    void updateTotalMemory(Context context) {
        mTotalMem = getSystemTotalMemory(context);
    }

    void updateAvailMemory(Context context) {
        mAvailMem = getSystemAvailMemory(context);
    }

    public String getTotalMemString(Context context) {
        if (mTotalMem == INVALID_MEM_SIZE) {
            updateTotalMemory(context);
        }
        return Formatter.formatShortFileSize(context, mTotalMem);
    }

    public String getAvailMemString(Context context) {
        if (mAvailMem == INVALID_MEM_SIZE) {
            updateAvailMemory(context);
        }
        return Formatter.formatShortFileSize(context, mAvailMem);
    }

    @VisibleForTesting
    public void setAvailMemSize(long size) {
        mAvailMem = size;
    }

    @VisibleForTesting
    public void setTotalMemSize(long size) {
        mTotalMem = size;
    }

}
