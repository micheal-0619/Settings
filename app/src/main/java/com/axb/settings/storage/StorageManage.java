package com.axb.settings.storage;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.R)
public class StorageManage {

    Context mContext;
    float unit1 = 1024, unit2 = 1000;

    public StorageManage(Context context) {
        this.mContext = context;
    }

    public String getMemoryInfo(File path, Context context) {
        long blockSize;
        long totalBlockCount;
        long availableCount;
        // TODO Auto-generated method stub
        StatFs stat = new StatFs(path.getPath());

        //检测系统版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            //获取每个扇区的大小
            blockSize = stat.getBlockSizeLong();

            //获取总共有多少扇区
            totalBlockCount = stat.getBlockCountLong();

            //获取可用扇区数量
            availableCount = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlockCount = stat.getBlockCount();
            availableCount = stat.getAvailableBlocks();
        }

        // 磁盘总大小
        String totalMemory = Formatter.formatFileSize(context, blockSize * totalBlockCount);
        String totalSize = getUnit(blockSize * totalBlockCount, unit2);
        // 可用大小
        String availableMemory = Formatter.formatFileSize(context, blockSize * availableCount);

        return totalSize + "##" + totalMemory + "##" + availableMemory;
    }


    /**
     * 进制转换
     */
    private static String[] units = {"B", "KB", "MB", "GB", "TB"};

    public static String getUnit(float size, float base) {
        int index = 0;
        while (size > base && index < 4) {
            size = size / base;
            index++;
        }
        return String.format(Locale.getDefault(), " %.2f %s ", size, units[index]);
    }

}
