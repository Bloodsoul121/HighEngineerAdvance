package com.blood.highengineeradvance.toutiao.channel;


import android.content.Context;
import android.view.View;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyAdapter;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyViewHolder;

import java.util.Collections;

public class ChannelAdapter extends BaseRecyAdapter<Channel> implements DragCallback.ItemTouchHelperAdapter{

    private final int mType;
    private boolean mIsShowImgCancel;

    public ChannelAdapter(Context context, int type) {
        super(context);
        this.mType = type;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.channel_item;
    }

    @Override
    protected void onBindItemViewHolder(BaseRecyViewHolder holder, Channel channel, int position) {
        holder.setText(R.id.item_channel_text_view, channel.name);
        holder.setVisibility(R.id.img_channel_cancel, mIsShowImgCancel ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemDissmiss(int position) {
        //移除数据
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void setShowImgCancel(boolean isShowImgCancel) {
        this.mIsShowImgCancel = isShowImgCancel;
        notifyDataSetChanged();
    }

}
