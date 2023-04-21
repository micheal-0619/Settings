package com.axb.settings.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axb.settings.R;

import java.util.List;

public class FactoryActivity extends AppCompatActivity {

    private static final String TAG = "FactoryActivity";
    private ImageView back;
    private View factory;
    private Context mContext;
    //settings
    private static final String PKG_SETTINGS = "com.android.settings";
    //settings
    private static final String CLS_SETTINGS = "com.android.settings.homepage.SettingsHomepageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        mContext = this;
        ((TextView) findViewById(R.id.txt_tittle_name)).setText("恢复出厂设置");

        factory = (View) findViewById(R.id.view_factory);
        back = (ImageView) findViewById(R.id.img_back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        factory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FactoryActivity.this, "恢复出厂设置", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent("com.android.settings.action.FACTORY_RESET");
                //startActivity(intent);

                startApp(mContext, PKG_SETTINGS, CLS_SETTINGS, null);
            }
        });
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

}