package com.axb.settings.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.axb.settings.R;
import com.axb.settings.utils.SystemBrightnessUtil;

public class BrightnessActivity extends AppCompatActivity {

    private ImageView back;
    private SeekBar seekBar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brihtness);
        mContext=this;

        ((TextView) findViewById(R.id.txt_tittle_name)).setText("亮度设置");

        back=(ImageView) findViewById(R.id.img_back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        seekBar=(SeekBar) findViewById(R.id.sbr_line);
        seekBar.setProgress(SystemBrightnessUtil.getSystemBrightness(mContext));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SystemBrightnessUtil.setSystemBrightness(progress, mContext);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                SystemBrightnessUtil.setSystemBrightness(seekBar.getProgress(), mContext);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}