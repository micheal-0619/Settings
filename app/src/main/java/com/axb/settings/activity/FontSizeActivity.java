package com.axb.settings.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.axb.settings.R;
import com.axb.settings.view.TickMarkSeekBar;

public class FontSizeActivity extends AppCompatActivity {

    private float[] currentSize = {0.85f, 1.0f, 1.15f, 1.3f};
    private TextView current_label;
    private TextView fontSize;
    private Context mContext;
    private TickMarkSeekBar tickMarkSeekBar;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size);
        mContext = this;

        initFontView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        float f = Settings.System.getFloat(mContext.getContentResolver(), "font_scale", 1.0f);
        tickMarkSeekBar.setProgress(getProgress(f));
        setCurrent_label(f);

    }

    private void initFontView() {
        fontSize = (TextView) findViewById(R.id.txt_tittle_name);
        fontSize.setText("字体大小");

        //返回
        back = (ImageView) findViewById(R.id.img_back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tickMarkSeekBar = (TickMarkSeekBar) findViewById(R.id.seek_bar);
        current_label = (TextView) findViewById(R.id.current_label);
        tickMarkSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Settings.System.putFloat(mContext.getContentResolver(), "font_scale", currentSize[progress]);
                setCurrent_label(currentSize[progress]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setCurrent_label(float f) {
        float[] fArr = this.currentSize;
        if (f == fArr[0]) {
            this.current_label.setText("小");
        } else if (f == fArr[1]) {
            this.current_label.setText("默认");
        } else if (f == fArr[2]) {
            this.current_label.setText("中");
        } else if (f == fArr[3]) {
            this.current_label.setText("大");
        }
    }

    private int getProgress(float f) {
        int i = 0;
        while (true) {
            float[] fArr = this.currentSize;
            if (i >= fArr.length) {
                return 0;
            }
            if (f == fArr[i]) {
                return i;
            }
            i++;
        }
    }
}