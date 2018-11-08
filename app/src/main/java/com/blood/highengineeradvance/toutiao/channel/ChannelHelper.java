package com.blood.highengineeradvance.toutiao.channel;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyAdapter;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ChannelHelper implements View.OnClickListener {

    private Context mContext;
    private ChannelDialog mChannelDialog;
    private List<Channel> mChannelDatas;
    private List<Channel> mAddChannels;
    private List<Channel> mUnaddChannels;
    private ChannelAdapter mAddAdapter;
    private ChannelAdapter mUnaddAdapter;
    private boolean mIsEdit;
    private ChannelTitleView mAddChannelTitleView;

    public ChannelHelper(Context context) {
        this.mContext = context;
    }

    public void showChannelDialog() {
        mChannelDialog = new ChannelDialog(mContext, R.layout.layout_more_channel_view, new int[0]);
        mChannelDialog.show();
        initDialog(mChannelDialog);
    }

    private void dismissChannelDialog() {
        mChannelDialog.dismiss();
    }

    private void initDialog(ChannelDialog dialog) {
        initStatus(dialog);
        initChannelData();
        initAddRecyclerView(dialog);
        initUnaddRecyclerView(dialog);
    }

    private void initStatus(ChannelDialog dialog) {
        ImageView imgCancel = dialog.findViewById(R.id.img_cancel);
        imgCancel.setOnClickListener(this);

        mAddChannelTitleView = dialog.findViewById(R.id.channel_add_title_view);
        mAddChannelTitleView.setTitle("我的频道");
        mAddChannelTitleView.setDescrible("点击进入频道");
        mAddChannelTitleView.setEdit("编辑");
        mAddChannelTitleView.setEditOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsEdit = !mIsEdit;
                if (mIsEdit) {
                    mAddChannelTitleView.setEdit("完成");
                    mAddAdapter.setShowImgCancel(true);
                } else {
                    mAddChannelTitleView.setEdit("编辑");
                    mAddAdapter.setShowImgCancel(false);
                }
            }
        });

        ChannelTitleView unaddChannelTitleView = dialog.findViewById(R.id.channel_unadd_title_view);
        unaddChannelTitleView.setTitle("频道推荐");
        unaddChannelTitleView.setDescrible("点击添加频道");
        unaddChannelTitleView.setEditVisible(View.GONE);
    }

    private void initAddRecyclerView(ChannelDialog dialog) {
        RecyclerView recyclerView = dialog.findViewById(R.id.add_channel_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        mAddAdapter = new ChannelAdapter(mContext, ChannelConstants.CHANNEL_TYPE_ADD);
        recyclerView.setAdapter(mAddAdapter);
        mAddAdapter.setData(mAddChannels);

        mAddAdapter.setOnItemLongClickListener(new BaseRecyAdapter.OnRecyclerViewItemLongClickListener<Channel>() {
            @Override
            public boolean onItemLongClick(BaseRecyViewHolder holder, Channel data, int position) {
                mIsEdit = true;
                mAddChannelTitleView.setEdit("完成");
                mAddAdapter.setShowImgCancel(true);
                return true;
            }
        });
        mAddAdapter.setOnItemClickListener(new BaseRecyAdapter.OnRecyclerViewItemClickListener<Channel>() {
            @Override
            public void onItemClick(BaseRecyViewHolder holder, Channel data, int position) {

            }
        });

        DragCallback dragCallback = new DragCallback(mAddAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initUnaddRecyclerView(ChannelDialog dialog) {
        RecyclerView recyclerView = dialog.findViewById(R.id.unadd_channel_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        mUnaddAdapter = new ChannelAdapter(mContext, ChannelConstants.CHANNEL_TYPE_UNADD);
        recyclerView.setAdapter(mUnaddAdapter);
        mUnaddAdapter.setData(mUnaddChannels);
        mUnaddAdapter.setOnItemClickListener(new BaseRecyAdapter.OnRecyclerViewItemClickListener<Channel>() {
            @Override
            public void onItemClick(BaseRecyViewHolder holder, Channel data, int position) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_cancel:
                dismissChannelDialog();
                break;
        }
    }

    private void initChannelData() {
        mChannelDatas = new ArrayList<>();
        mAddChannels = initAddChannel();
        mUnaddChannels = initUnaddChannel();
        mChannelDatas.addAll(mAddChannels);
        mChannelDatas.addAll(mUnaddChannels);
    }

    private List<Channel> initUnaddChannel() {
        List<Channel> list = new ArrayList<>();
        list.add(new Channel("科技", ChannelConstants.CHANNEL_TYPE_UNADD));
        list.add(new Channel("财经", ChannelConstants.CHANNEL_TYPE_UNADD));
        list.add(new Channel("新闻", ChannelConstants.CHANNEL_TYPE_UNADD));
        list.add(new Channel("军事", ChannelConstants.CHANNEL_TYPE_UNADD));
        list.add(new Channel("体育", ChannelConstants.CHANNEL_TYPE_UNADD));
        list.add(new Channel("直播", ChannelConstants.CHANNEL_TYPE_UNADD));
        list.add(new Channel("健康", ChannelConstants.CHANNEL_TYPE_UNADD));
        list.add(new Channel("房产", ChannelConstants.CHANNEL_TYPE_UNADD));
        return list;
    }

    private List<Channel> initAddChannel() {
        List<Channel> list = new ArrayList<>();
        list.add(new Channel("关注", ChannelConstants.CHANNEL_TYPE_ADD));
        list.add(new Channel("推荐", ChannelConstants.CHANNEL_TYPE_ADD));
        list.add(new Channel("热点", ChannelConstants.CHANNEL_TYPE_ADD));
        list.add(new Channel("深圳", ChannelConstants.CHANNEL_TYPE_ADD));
        list.add(new Channel("视频", ChannelConstants.CHANNEL_TYPE_ADD));
        list.add(new Channel("图片", ChannelConstants.CHANNEL_TYPE_ADD));
        list.add(new Channel("问答", ChannelConstants.CHANNEL_TYPE_ADD));
        list.add(new Channel("娱乐", ChannelConstants.CHANNEL_TYPE_ADD));
        return list;
    }

}
