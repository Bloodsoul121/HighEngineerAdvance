package com.blood.highengineeradvance.toutiao.channel2;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static com.blood.highengineeradvance.toutiao.channel.ChannelConstants.CHANNEL_TYPE_ADD;
import static com.blood.highengineeradvance.toutiao.channel.ChannelConstants.CHANNEL_TYPE_UNADD;
import static com.blood.highengineeradvance.toutiao.channel2.ChannelHelper2.CHANNEL_STATUS_CLICK;

public class DragCallback2 extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter mAdapter;

    private Paint mPaint;

    public interface ItemTouchHelperAdapter {
        //数据交换
        void onItemMove(int fromPosition, int toPosition);
        //数据删除
        void onItemDissmiss(int position);
        //状态
        int getStatus();
    }

    public DragCallback2(ItemTouchHelperAdapter adapter) {
        this.mAdapter = adapter;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#5e5e5e"));
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        int swipeFlags;
        int type = viewHolder.getItemViewType();
        boolean isUnaddChannelsEnable = mAdapter.getStatus() == CHANNEL_STATUS_CLICK;
        if (type == CHANNEL_TYPE_ADD || (isUnaddChannelsEnable && type == CHANNEL_TYPE_UNADD)) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;
        } else {
            dragFlags = 0;
            swipeFlags = 0;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        //onItemMove是接口方法
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),viewHolder1.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //onItemDissmiss是接口方法
        mAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ItemTouchHelper.ACTION_STATE_IDLE){
            return;
        }
        viewHolder.itemView.setScaleX(1.1f);
        viewHolder.itemView.setScaleY(1.1f);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setScaleX(1f);
        viewHolder.itemView.setScaleY(1f);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            if (dX != 0 && dY != 0 || isCurrentlyActive) {
//                //长按拖拽时底部绘制一个虚线矩形
//                c.drawRect(viewHolder.itemView.getLeft(),viewHolder.itemView.getTop(),viewHolder.itemView.getRight(),viewHolder.itemView.getBottom(),mPaint);
//            }
    }
}
