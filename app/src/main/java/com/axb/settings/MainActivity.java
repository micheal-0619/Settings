package com.axb.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.axb.settings.activity.AboutActivity;
import com.axb.settings.activity.AudioActivity;
import com.axb.settings.activity.DisplayActivity;
import com.axb.settings.activity.FontSizeActivity;
import com.axb.settings.adapter.SettingAdapter;
import com.axb.settings.bean.SetItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //RecyclerView
    private RecyclerView mSetRecyclerView;
    private SettingAdapter mSettingAdapter;
    private Context mContext;
    private static final int COLUMN = 1;
    private List<SetItem> mSetItem;

    //双击退出
    private ImageView back;
    private long firstClickBack = 0;

    //选项卡定义
    private static final int SETTING_TAB_NETWORK = 0;
    private static final int SETTING_TAB_BLUETOOTH = 1;
    private static final int SETTING_TAB_DISPLAY = 2;
    private static final int SETTING_TAB_SOUND = 3;
    private static final int SETTING_TAB_DATE = 4;
    private static final int SETTING_TAB_STORAGE = 5;
    private static final int SETTING_TAB_APP_NOTIFICATION = 6;
    private static final int SETTING_TAB_PASSWORD = 7;
    private static final int SETTING_TAB_PARENTAL_CONTROL = 8;
    private static final int SETTING_TAB_ABOUT_TABLETS = 9;
    private static final int SETTING_TAB_RESTORE_FACTORY = 10;


    //settings
    private static final String PKG_SETTINGS = "com.android.settings";
    //wifi
    private static final String CLS_WIFI = "com.android.settings.wifi.WifiPickerActivity";
    //bluetooth
    private static final String CLS_BLUETOOTH = "com.android.settings.Settings$ConnectedDeviceDashboardActivity";

    private static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView() {

        //back
        back = (ImageView) findViewById(R.id.back_set);
        back.setOnClickListener(this);

        //RecyclerView
        mSetItem = getItemList();
        mContext = MainActivity.this;
        mSetRecyclerView = findViewById(R.id.recyclerview_setting);
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext, COLUMN);
        mSetRecyclerView.setLayoutManager(layoutManager);
        mSettingAdapter = new SettingAdapter(R.layout.item_rv, mSetItem);
        //mSettingAdapter = new SettingAdapter();
        mSetRecyclerView.setAdapter(mSettingAdapter);

    }

    /**
     * 获取Adapter数据源
     *
     * @return itemList
     */
    private List<SetItem> getItemList() {
        List<SetItem> itemList = new ArrayList<>();
        //图片数组资源
        TypedArray typedArray = getResources().obtainTypedArray(R.array.itemList);
        int[] intArray = new int[typedArray.length()];
        //遍历
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = typedArray.getResourceId(i, 0);
            SetItem item = new SetItem(intArray[i]);
            itemList.add(item);
        }
        return itemList;
    }

    /*监听点击事件 recyclerview*/

    private void initListener() {
        mSettingAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                settingOption(position);
                Toast.makeText(mContext, "你点击了第 " + position + " 个选项", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void settingOption(int position) {
        switch (position) {
            case SETTING_TAB_NETWORK:
                startApp(mContext, PKG_SETTINGS, CLS_WIFI, null);
                //Toast.makeText(mContext, "我是网络", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_BLUETOOTH:
                startApp(mContext, PKG_SETTINGS, CLS_BLUETOOTH, null);
                //Toast.makeText(mContext, "我是蓝牙", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_DISPLAY:
                intent = new Intent(mContext, DisplayActivity.class);
                startActivity(intent);
                //Toast.makeText(mContext, "我是显示", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_SOUND:
                intent = new Intent(mContext, AudioActivity.class);
                startActivity(intent);
                //Toast.makeText(mContext, "我是声音", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_DATE:
                Toast.makeText(mContext, "我是日期", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_STORAGE:
                Toast.makeText(mContext, "我是存储", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_APP_NOTIFICATION:
                Toast.makeText(mContext, "我是应用和通知", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_PASSWORD:
                Toast.makeText(mContext, "我是密码管理", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_PARENTAL_CONTROL:
                Toast.makeText(mContext, "我是家长管控", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_ABOUT_TABLETS:
                intent = new Intent(mContext, AboutActivity.class);
                startActivity(intent);
                //Toast.makeText(mContext, "我是关于平板", Toast.LENGTH_SHORT).show();
                break;
            case SETTING_TAB_RESTORE_FACTORY:
                Toast.makeText(mContext, "我是恢复出厂设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 根据包名,类名,intent 打开应用
     *
     * @param context 上下文
     * @param pkg     应用包名
     * @param cls     Activity类名
     * @param intent  intent
     */
    public static void startApp(Context context, String pkg, String cls, Intent intent) {
        boolean appInstalled = checkAppInstalled(context, pkg);
        //判断App是否存在，否则弹出提示
        if (appInstalled) {
            if (intent == null) {
                //Intent intent = new Intent();
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            }
            ComponentName cn = new ComponentName(pkg, cls);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cn);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "应用不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据包名,判断apk是否安装
     *
     * @param context 上下文
     * @param pkgName 应用包名
     */
    private static boolean checkAppInstalled(Context context, String pkgName) {
        if (pkgName == null || pkgName.isEmpty()) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        if (info == null || info.isEmpty())
            return false;
        for (int i = 0; i < info.size(); i++) {
            if (pkgName.equals(info.get(i).packageName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 全局的 沉浸式状态栏/导航栏
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    ////双击退出
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_set:
                exitClick();
                break;
        }
    }

    private void exitClick() {
        long secondClickBack = System.currentTimeMillis();
        if (secondClickBack - firstClickBack > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
            firstClickBack = secondClickBack;
        } else {
            finish();
        }
    }
}