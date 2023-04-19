package com.axb.settings.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axb.settings.R;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {

    private View fontSize;
    private View brightness;
    private View eyeProtectionMode;
    private ImageView back;

    private static Intent intent;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ((TextView)findViewById(R.id.txt_tittle_name)).setText("显示设置");
        mContext = this;
        initDisplayView();
    }

    private void initDisplayView() {
        //back
        back = (ImageView) findViewById(R.id.img_back_icon);
        back.setOnClickListener(this);

        fontSize = (View) findViewById(R.id.view_font);
        fontSize.setOnClickListener(this);

        brightness = (View) findViewById(R.id.view_brightness);
        brightness.setOnClickListener(this);

        eyeProtectionMode = (View) findViewById(R.id.view_eye);
        eyeProtectionMode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back_icon:
                finish();
                break;
            case R.id.view_font:
                intent = new Intent(mContext, FontSizeActivity.class);
                startActivity(intent);
                break;
            case R.id.view_brightness:
                intent = new Intent(mContext, BrightnessActivity.class);
                startActivity(intent);
                //Toast.makeText(mContext, "我是屏幕亮度", Toast.LENGTH_SHORT).show();
                break;
            case R.id.view_eye:
                intent = new Intent(mContext, EyesProtectActivity.class);
                startActivity(intent);
                //Toast.makeText(mContext, "我是护眼模式", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}