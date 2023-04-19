package com.axb.settings.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.axb.settings.R;
import com.axb.settings.utils.SharePrefUtils;

public class EyesProtectActivity extends AppCompatActivity {

    private static final String TAG = "EyesProtectActivity";
    private ImageView back;
    private Context mContext;
    private static final String KEY_DISPLAY_MODE = "display_night_mode";
    private static final String KEY_DISPLAY_PROGRESS = "display_night_progress";
    private static final String KEY_DISPLAY_MSG = "display_msg";
    private static final String X_NIGHTMODE_CLOSE = "x.nightmode.close";
    private static final String X_NIGHTMODE_LEVEL = "x.nightmode.level";
    private static final String X_NIGHTMODE_OPEN = "x.nightmode.open";

    private SeekBar sbr_set_mode_night;
    private Switch switch_btn_night_mode;
    private View view_sett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes_protect);
        mContext = this;
        initEyeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isNight = SharePrefUtils.getBoolean(KEY_DISPLAY_MODE, false);
        switch_btn_night_mode.setChecked(isNight);
        sbr_set_mode_night.setProgress(SharePrefUtils.getInt(KEY_DISPLAY_PROGRESS, 100));
        if (isNight) {
            view_sett.setVisibility(View.VISIBLE);
        } else {
            view_sett.setVisibility(View.GONE);
        }
    }

    private void initEyeView() {

        ((TextView) findViewById(R.id.txt_tittle_name)).setText("护眼模式");

        back = (ImageView) findViewById(R.id.img_back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharePrefUtils.init(mContext, KEY_DISPLAY_MSG);//初始化不显示进度条


        switch_btn_night_mode = (Switch) findViewById(R.id.switch_btn_night_mode);
        sbr_set_mode_night = (SeekBar) findViewById(R.id.sbr_set_mode_night);
        view_sett = findViewById(R.id.view_sett);

        switch_btn_night_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nightModeControl(buttonView, isChecked);
            }
        });

        sbr_set_mode_night.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              /*  Intent intent = new Intent(X_NIGHTMODE_LEVEL);
                intent.putExtra("level", progress);
                sendBroadcast(intent);
                SharePrefUtils.putInt(KEY_DISPLAY_PROGRESS, progress);*/
                SharePrefUtils.instance(mContext).changeColor(progress);
                Log.d(TAG, "onProgressChanged: progress== " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /***
     * 护眼模式控制进度条显示控制
     * @param buttonView 按钮
     * @param isChecked 判断是否显示
     * **/
    private void nightModeControl(CompoundButton buttonView, boolean isChecked) {
        Intent intent;
        if (isChecked) {
            intent = new Intent(X_NIGHTMODE_OPEN);
            view_sett.setVisibility(View.VISIBLE);
            int i = SharePrefUtils.getInt(KEY_DISPLAY_PROGRESS, 100);
            Intent intent2 = new Intent(X_NIGHTMODE_LEVEL);
            intent2.putExtra("level", i);
            sendBroadcast(intent2);
        } else {
            intent = new Intent(X_NIGHTMODE_CLOSE);
            view_sett.setVisibility(View.GONE);
        }
        sendBroadcast(intent);
        SharePrefUtils.putBoolean(KEY_DISPLAY_MODE, isChecked);
    }

}