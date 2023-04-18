package com.axb.settings.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.axb.settings.bean.SetItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.axb.settings.R;

import java.util.List;

public class SettingAdapter extends BaseQuickAdapter<SetItem, BaseViewHolder> {

    public SettingAdapter(int layoutResId, @Nullable List<SetItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, SetItem setItem) {

        baseViewHolder.setText(R.id.setting_text, setItem.getId());
        baseViewHolder.setImageResource(R.id.setting_arrows, R.drawable.ic_set_arrows);
    }
}
