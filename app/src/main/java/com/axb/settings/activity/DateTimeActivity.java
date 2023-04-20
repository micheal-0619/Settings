package com.axb.settings.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.axb.settings.R;
import com.axb.settings.utils.DateUtil;
import com.axb.settings.utils.SharePrefUtils;

import android.provider.Settings;

import java.util.Date;
import java.util.TimeZone;

public class DateTimeActivity extends AppCompatActivity {

    private static final String TAG = "DateTimeActivity";

    private TextView date;
    private TextView time;
    private TextView timeZone;
    private Context mContext;
    private ImageView back;

    private static final String KEY_SWITCH_BUTTON = "witch_button";
   // private Switch switch24H;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        mContext = this;

        initView();
    }

    private void initView() {

        ((TextView) findViewById(R.id.txt_tittle_name)).setText("时间和日期");

        back = (ImageView) findViewById(R.id.img_back_icon);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        timeZone = (TextView) findViewById(R.id.time_zone);
        //switch24H = (Switch) findViewById(R.id.switch_24_hour);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        date.setText(DateUtil.getNowDate() + " " + DateUtil.getWeekOfDate(new Date()));
        time.setText(DateUtil.getNowTime());

        //import java.util.TimeZone;
        TimeZone zone = TimeZone.getDefault();
        String zoneID = zone.getID();  //Asia/Shanghai
        String currentZone = zone.getDisplayName();//获取当前系统时区
        Log.d(TAG, "initView: nowZone = " + currentZone);
        if (null != getZone(zoneID)) {
            timeZone.setText(getZone(zoneID));
        } else {
            timeZone.setText(currentZone);
        }

      /*  switch24H.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isOpen) {
                String timeResult = DateUtil.get24Hour(mContext, isOpen);
                Log.d(TAG, "onCheckedChanged:timeResult == " + timeResult);
                time.setText(timeResult);
            }
        });*/

    }

    //一次次的从xml文件获取信息
    public String getZone(String zoneID) {
        try {
            //获取信息的方法
            Resources res = getResources();
            XmlResourceParser xrp = res.getXml(R.xml.timezones);
            //判断是否已经到了文件的末尾
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String name = xrp.getName();
                    Log.d(TAG, "getZone: name " + name);
                    if (name.equals("timezone")) {
                        Log.d(TAG, "getZone: " + xrp.getAttributeValue(0));
                        //关键词搜索，如果匹配，那么添加进去如果不匹配就不添加，如果没输入关键词“”,那么默认搜索全部
                        if (xrp.getAttributeValue(0).indexOf(zoneID) != -1) {
                            //0，标识id，1标识名称
                            Log.d(TAG, "getZone:11111 " + xrp.getAttributeValue(1));
                            return xrp.getAttributeValue(1);
                        }
                    }
                }
                //搜索过第一个信息后，接着搜索下一个
                xrp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}