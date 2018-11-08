package com.blood.highengineeradvance.toutiao.channel;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blood.highengineeradvance.R;

public class ChannelTitleView extends RelativeLayout {

    private TextView mTitle;
    private TextView mDescrible;
    private TextView mEdit;

    public ChannelTitleView(Context context) {
        super(context);
    }

    public ChannelTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.channel_title, this, true);
        mTitle = findViewById(R.id.title);
        mDescrible = findViewById(R.id.describle);
        mEdit = findViewById(R.id.edit);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setDescrible(String describle) {
        mDescrible.setText(describle);
    }

    public void setEdit(String edit) {
        mEdit.setText(edit);
    }

    public void setEditVisible(int visible) {
        mEdit.setVisibility(visible);
    }

    public void setEditOnClickListener(OnClickListener listener) {
        mEdit.setOnClickListener(listener);
    }

}
