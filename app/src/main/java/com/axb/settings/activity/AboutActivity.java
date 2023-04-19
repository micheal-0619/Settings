package com.axb.settings.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axb.settings.R;

import java.lang.reflect.Method;
import java.util.List;

public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";
    private ImageView back;
    private TextView modelInfo;
    private TextView SNInfo;
    private View updateSystem;
    public static final String PKG_OTA = "com.adups.fota";
    public static final String CLS_OTA = "com.adups.fota.GoogleOtaClient";
    private View clickToSetting;
    public static final String PKG_SETTING = "com.android.settings";
    public static final String CLS_SETTING = "com.android.settings.homepage.SettingsHomepageActivity";

    private Context mContext;
    private int mHitCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mContext = AboutActivity.this;
        initAboutView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHitCountdown = 7;//连续点击7次
    }

    private void initAboutView() {
        //返回
        back = (ImageView) findViewById(R.id.img_back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取型号model
        modelInfo = (TextView) findViewById(R.id.txt_model_info);
        modelInfo.setText(getModel());

        //获取SN号
        SNInfo = (TextView) findViewById(R.id.txt_sn_info);
        SNInfo.setText(getDeviceSN());

        //点击进入ota
        updateSystem = findViewById(R.id.view_system_update);
        updateSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startApp(mContext, PKG_OTA, CLS_OTA, null);
            }
        });

        //连续点击型号进入系统设置
        clickToSetting = findViewById(R.id.view_model);
        clickToSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goToSet()) {
                    startApp(mContext, PKG_SETTING, CLS_SETTING, null);
                }
            }
        });

    }

    /**
     * 获取型号model
     *
     * @return
     */
    private static String getModel() {
        String model = Build.MODEL;
        //Log.d(TAG, "getModel: model== " + model);
        if (null != model) {
            model = model.trim().replaceAll("\\s", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 获取Android设备 SN号
     *
     * @return
     */
    private static String getDeviceSN() {
        String serial = null;

        try {

            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
            //Log.d("111111 getDeviceSN >>", "get.invoke>>:" + get.invoke(c, "ro.serialno"));
            //Log.d("111111 getDeviceSN >>", "serial>>:" + serial);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
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
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            ComponentName cn = new ComponentName(pkg, cls);
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

    //连续点击
    private boolean goToSet() {
        if (mHitCountdown > 0) {
            mHitCountdown--;
        } else {
            //Toast.makeText(mContext, "连续点击了我", Toast.LENGTH_LONG).show();
            //Log.d("axb_lh 1111111", "mHitCountdown = " + mHitCountdown);
            return true;
        }
        return false;
    }
}