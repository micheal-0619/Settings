package com.axb.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.axb.settings.adapter.SettingAdapter;
import com.axb.settings.bean.SetItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //RecyclerView
    private RecyclerView mSetRecyclerView;
    private SettingAdapter mSettingAdapter;
    private Context mContext;
    private static final int COLUMN = 1;
    private List<SetItem> mSetItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

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
}