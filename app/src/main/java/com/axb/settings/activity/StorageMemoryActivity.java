package com.axb.settings.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.axb.settings.R;
import com.axb.settings.memory.MemoryManage;
import com.axb.settings.storage.StorageManage;
import com.axb.settings.storage.StorageQueryUtil;

import java.io.File;

@RequiresApi(api = Build.VERSION_CODES.R)
public class StorageMemoryActivity extends AppCompatActivity {

    private static final String TAG = "StorageMemoryActivity";
    private ImageView back;
    private TextView memory;
    private TextView storage;
    private Context mContext;
    private MemoryManage mMemoryManage;
    private StorageManage mStorageManage;
    // 获取存储空间
    File path;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_memory);
        mContext = this;
        ((TextView) findViewById(R.id.txt_tittle_name)).setText("内存和存储");

        mMemoryManage = new MemoryManage(mContext);
        mStorageManage = new StorageManage(mContext);
        memory = (TextView) findViewById(R.id.memory);
        storage = (TextView) findViewById(R.id.storage);
        path = Environment.getDataDirectory();

        back = (ImageView) findViewById(R.id.img_back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        memory.setText(getString(R.string.memory_summary, mMemoryManage.getAvailMemString(mContext), mMemoryManage.getTotalMemString(mContext)));
        Log.d(TAG, "onCreate: " + mMemoryManage.getAvailMemString(mContext)
                + " " + mMemoryManage.getTotalMemString(mContext));

        Log.d(TAG, "onCreate: storage " + mStorageManage.getMemoryInfo(path, mContext));

        StorageQueryUtil.queryWithStorageManager(mContext);

    }
}