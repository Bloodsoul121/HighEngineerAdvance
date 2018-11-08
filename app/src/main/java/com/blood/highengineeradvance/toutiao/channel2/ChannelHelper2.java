package com.blood.highengineeradvance.toutiao.channel2;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.toutiao.channel.Channel;
import com.blood.highengineeradvance.toutiao.channel.ChannelConstants;
import com.blood.highengineeradvance.toutiao.channel.ChannelDialog;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyAdapter;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyViewHolder;
import com.blood.highengineeradvance.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.blood.highengineeradvance.toutiao.channel.ChannelConstants.CHANNEL_TYPE_ADD;
import static com.blood.highengineeradvance.toutiao.channel.ChannelConstants.CHANNEL_TYPE_CLOSE;
import static com.blood.highengineeradvance.toutiao.channel.ChannelConstants.CHANNEL_TYPE_TITLE_ADD;
import static com.blood.highengineeradvance.toutiao.channel.ChannelConstants.CHANNEL_TYPE_TITLE_UNADD;
import static com.blood.highengineeradvance.toutiao.channel.ChannelConstants.CHANNEL_TYPE_UNADD;

public class ChannelHelper2 {

    private Context mContext;
    private ChannelDialog mChannelDialog;
    private List<Channel> mChannelDatas;
    private ChannelAdapter mAdapter;

    public ChannelHelper2(Context context) {
        this.mContext = context;
    }

    public void showChannelDialog() {
        mChannelDialog = new ChannelDialog(mContext, R.layout.layout_more_channel_view2, new int[0]);
        mChannelDialog.show();
        initDialog(mChannelDialog);
    }

    private void dismissChannelDialog() {
        mChannelDialog.dismiss();
    }

    private void initDialog(ChannelDialog dialog) {
        initChannelData();
        initChannelView(dialog);
    }

    private void initChannelData() {
        mChannelDatas = new ArrayList<>();
        mChannelDatas.add(new Channel("关注", CHANNEL_TYPE_ADD));
        mChannelDatas.add(new Channel("推荐", CHANNEL_TYPE_ADD));
        mChannelDatas.add(new Channel("热点", CHANNEL_TYPE_ADD));
        mChannelDatas.add(new Channel("深圳", CHANNEL_TYPE_ADD));
        mChannelDatas.add(new Channel("视频", CHANNEL_TYPE_ADD));
        mChannelDatas.add(new Channel("图片", CHANNEL_TYPE_ADD));
        mChannelDatas.add(new Channel("问答", CHANNEL_TYPE_ADD));
        mChannelDatas.add(new Channel("娱乐", CHANNEL_TYPE_ADD));
        mChannelDatas.add(new Channel("科技", CHANNEL_TYPE_UNADD));
        mChannelDatas.add(new Channel("财经", CHANNEL_TYPE_UNADD));
        mChannelDatas.add(new Channel("新闻", CHANNEL_TYPE_UNADD));
        mChannelDatas.add(new Channel("军事", CHANNEL_TYPE_UNADD));
        mChannelDatas.add(new Channel("体育", CHANNEL_TYPE_UNADD));
        mChannelDatas.add(new Channel("直播", CHANNEL_TYPE_UNADD));
        mChannelDatas.add(new Channel("健康", CHANNEL_TYPE_UNADD));
        mChannelDatas.add(new Channel("房产", CHANNEL_TYPE_UNADD));
    }

    private void initChannelView(ChannelDialog dialog) {
        RecyclerView recyclerView = dialog.findViewById(R.id.channel_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (mAdapter.getViewType(i) == CHANNEL_TYPE_TITLE_ADD || mAdapter.getViewType(i) == CHANNEL_TYPE_TITLE_UNADD
                        || mAdapter.getViewType(i) == CHANNEL_TYPE_CLOSE) {
                    return 4;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new ChannelAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(mChannelDatas);

        mAdapter.setOnItemLongClickListener(new BaseRecyAdapter.OnRecyclerViewItemLongClickListener<Channel>() {
            @Override
            public boolean onItemLongClick(BaseRecyViewHolder holder, Channel data, int position) {
                LogUtil.i("onItemLongClick " + data + " " + position);
                if (data == null) {
                    return false;
                }
                if (data.type == ChannelConstants.CHANNEL_TYPE_ADD) {
                    mAdapter.setChannelEdit(true);
                    return true;
                }
                return false;
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyAdapter.OnRecyclerViewItemClickListener<Channel>() {
            @Override
            public void onItemClick(BaseRecyViewHolder holder, Channel data, int position) {
                LogUtil.i("onItemClick" + data + " " + position);
                if (position == 0) {
                    dismissChannelDialog();
                    return;
                }
                if (data == null) {
                    return;
                }
                int type = data.type;
                if (type == ChannelConstants.CHANNEL_TYPE_ADD) {
                    if (mAdapter.isEdit()) {
                        mAdapter.transformToUnaddChannels(position);
                    } else {
                        Toast.makeText(mContext, data.name, Toast.LENGTH_SHORT).show();
                    }
                }
                if (type == ChannelConstants.CHANNEL_TYPE_UNADD) {
                    mAdapter.transformToAddChannels(position);
                }
            }
        });

        DragCallback2 dragCallback = new DragCallback2(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public static final int CHANNEL_STATUS_NORMAL = 0x00;
    public static final int CHANNEL_STATUS_DRAG = 0x01;
    public static final int CHANNEL_STATUS_CLICK = 0x02;

    private static class ChannelAdapter extends BaseRecyAdapter<Channel> implements DragCallback2.ItemTouchHelperAdapter, View.OnClickListener {

        private boolean mIsEdit;
        private int mAddChannelTitlePosition;
        private int mUnaddChannelTitlePosition;
        private int mAddChannelsCount;
        private boolean mIsClickItem;

        private ChannelAdapter(Context context) {
            super(context);
        }

        @Override
        public void setData(List<Channel> data) {
            mAddChannelsCount = 0;
            for (Channel channel : data) {
                if (channel.type == CHANNEL_TYPE_ADD) {
                    mAddChannelsCount++;
                }
            }
            super.setData(data);
        }

        @Override
        public int getItemCount() {
            return super.getItemCount() + 3;
        }

        @Override
        protected int getViewType(int position) {
            int type;
            if (position == 0) {
                type = CHANNEL_TYPE_CLOSE;
            } else if (position == 1) {
                mAddChannelTitlePosition = position;
                type = CHANNEL_TYPE_TITLE_ADD;
            } else if (mAddChannelsCount + 1 >= position) {
                type = CHANNEL_TYPE_ADD;
            } else if (mAddChannelsCount + 2 == position) {
                mUnaddChannelTitlePosition = position;
                type = CHANNEL_TYPE_TITLE_UNADD;
            } else {
                type = CHANNEL_TYPE_UNADD;
            }
            return type;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            if (viewType == CHANNEL_TYPE_TITLE_ADD || viewType == CHANNEL_TYPE_TITLE_UNADD) {
                return R.layout.channel_title2;
            } else if (viewType == CHANNEL_TYPE_CLOSE) {
                return R.layout.channel_close;
            }
            return R.layout.channel_item;
        }

        @Override
        protected Channel getItemData(BaseRecyViewHolder holder, int position) {
            int type = holder.getItemViewType();
            if (type == CHANNEL_TYPE_ADD) {
                return mDatas.get(position - 2);
            }
            if (type == CHANNEL_TYPE_UNADD) {
                return mDatas.get(position - 3);
            }
            return null;
        }

        @Override
        protected void onBindItemViewHolder(BaseRecyViewHolder holder, Channel channel, int position) {
            int type = holder.getItemViewType();
            if (type == CHANNEL_TYPE_ADD) {
                holder.setText(R.id.item_channel_text_view, channel.name);
                holder.setVisibility(R.id.img_channel_cancel, mIsEdit ? View.VISIBLE : View.GONE);
            } else if (type == CHANNEL_TYPE_UNADD) {
                holder.setText(R.id.item_channel_text_view, channel.name);
                holder.setVisibility(R.id.img_channel_cancel, View.GONE);
            } else if (type == CHANNEL_TYPE_TITLE_ADD) {
                holder.setText(R.id.title, "我的频道");
                holder.setText(R.id.describle, "点击进入频道");
                holder.setText(R.id.edit, mIsEdit ? "完成" : "编辑");
                holder.setVisibility(R.id.edit, View.VISIBLE);
                holder.setOnClickListener(R.id.edit, this);
            } else if (type == CHANNEL_TYPE_TITLE_UNADD) {
                holder.setText(R.id.title, "频道推荐");
                holder.setText(R.id.describle, "点击添加频道");
                holder.setVisibility(R.id.edit, View.GONE);
            }
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            //交换位置
            int fromIndex;
            int toIndex;
            if (checkAddChannelsPosition(fromPosition) && checkAddChannelsPosition(toPosition)) {
                fromIndex = fromPosition - 2;
                toIndex = toPosition - 2;
                Channel channel = mDatas.remove(fromIndex);
                mDatas.add(toIndex, channel);
                notifyItemMoved(fromPosition,toPosition);
            }
            if (!mIsClickItem) {
                return;
            }
            if (checkAddChannelsPosition(fromPosition) && checkUnaddChannelsPosition(toPosition)) {
                fromIndex = fromPosition - 2;
                toIndex = toPosition - 3 - 1;
                Channel channel = mDatas.remove(fromIndex);
                channel.type = CHANNEL_TYPE_UNADD;
                mDatas.add(toIndex, channel);
                mAddChannelsCount--;
                notifyItemMoved(fromPosition,toPosition - 1);
                notifyItemRangeChanged(fromPosition, toPosition - fromPosition + 1);
            } else if (checkUnaddChannelsPosition(fromPosition) && checkAddChannelsPosition(toPosition)) {
                fromIndex = fromPosition - 3;
                toIndex = toPosition - 2;
                Channel channel = mDatas.remove(fromIndex);
                channel.type = CHANNEL_TYPE_ADD;
                mDatas.add(toIndex, channel);
                mAddChannelsCount++;
                notifyItemMoved(fromPosition,toPosition);
                notifyItemRangeChanged(toPosition, fromPosition - toPosition + 1);
            }
        }

        @Override
        public void onItemDissmiss(int position) {
            //移除数据
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public int getStatus() {
            if (mIsClickItem) {
                return CHANNEL_STATUS_CLICK;
            }
            return CHANNEL_STATUS_NORMAL;
        }

        private void setChannelEdit(boolean isEdit) {
            mIsEdit = isEdit;
            notifyDataSetChanged();
        }

        public boolean isEdit() {
            return mIsEdit;
        }

        public void transformToUnaddChannels(int position) {
            mIsClickItem = true;
            onItemMove(position, mAddChannelsCount + 3);
            mIsClickItem = false;
        }

        public void transformToAddChannels(int position) {
            mIsClickItem = true;
            onItemMove(position, mAddChannelsCount + 2);
            mIsClickItem = false;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.edit:
                    mIsEdit = !mIsEdit;
                    setChannelEdit(mIsEdit);
                    break;
            }
        }

        private boolean checkAddChannelsPosition(int position) {
            return mAddChannelTitlePosition < position && (mIsClickItem ? position <= mUnaddChannelTitlePosition : position < mUnaddChannelTitlePosition);
        }

        private boolean checkUnaddChannelsPosition(int position) {
            return position > mUnaddChannelTitlePosition;
        }

    }

}
