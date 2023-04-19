package com.axb.settings.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.axb.settings.R;

public class AudioActivity extends AppCompatActivity {
    private ImageView back;
    private AudioManager mAudioManager;
    private SettingsContentObserver mSettingsContentObserver;
    SeekBar music_seekBar;
    SeekBar sbr_alarm_audio;
    SeekBar sbr_notifications_audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ((TextView) findViewById(R.id.txt_tittle_name)).setText("声音设置");

        initAudioView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterVolumeChangeReceiver();//销毁广播
    }

    private void initAudioView() {

        back = (ImageView) findViewById(R.id.img_back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerVolumeChangeReceiver();//注册广播

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        music_seekBar = (SeekBar) findViewById(R.id.sbr_music_audio);
        sbr_notifications_audio = (SeekBar) findViewById(R.id.sbr_notifications_audio);
        sbr_alarm_audio = (SeekBar) findViewById(R.id.sbr_alarm_audio);

        //媒体音量
        music_seekBar.setMax(mAudioManager.getStreamMaxVolume(3));
        music_seekBar.setProgress(mAudioManager.getStreamVolume(3));
        music_seekBar.setSoundEffectsEnabled(false);
        music_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(3, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //铃声音量
        sbr_notifications_audio.setMax(mAudioManager.getStreamMaxVolume(5));
        sbr_notifications_audio.setProgress(mAudioManager.getStreamVolume(5));
        sbr_notifications_audio.setSoundEffectsEnabled(false);
        sbr_notifications_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(5, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //闹钟音量
        sbr_alarm_audio.setMax(mAudioManager.getStreamMaxVolume(5));
        sbr_alarm_audio.setProgress(mAudioManager.getStreamVolume(5));
        sbr_alarm_audio.setSoundEffectsEnabled(false);
        sbr_alarm_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(4, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void registerVolumeChangeReceiver() {
        mSettingsContentObserver = new SettingsContentObserver(this, new Handler());
        getApplicationContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, mSettingsContentObserver);
    }

    private void unregisterVolumeChangeReceiver() {
        getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);
    }

    //监听广播
    public class SettingsContentObserver extends ContentObserver {
        Context mContext;

        public SettingsContentObserver(Context context, Handler handler) {
            super(handler);
            mContext = context;
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            sbr_alarm_audio.setProgress(mAudioManager.getStreamVolume(4));
            sbr_notifications_audio.setProgress(mAudioManager.getStreamVolume(5));
            music_seekBar.setProgress(mAudioManager.getStreamVolume(3));
        }
    }
}