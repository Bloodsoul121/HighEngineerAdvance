package com.blood.highengineeradvance.view.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyAdapter;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        init();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyAdapter adapter = new RecyAdapter(this);
        recyclerView.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("" + i);
        }
        adapter.setData(list);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private static class RecyAdapter extends BaseRecyAdapter<String> {

        private RecyAdapter(Context context) {
            super(context);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.layout_item_recy;
        }

        @Override
        protected void onBindItemViewHolder(BaseRecyViewHolder holder, String s, int position) {
            holder.setText(R.id.item_tv, mDatas.get(position));
        }
    }

    private class RecyItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
        private int VERTICAL = LinearLayoutManager.VERTICAL;
        private final Drawable mDividerDrawable;
        private int mOrientation;

        public RecyItemDecoration(Context context, int orientation) {
            super();
            TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
            mDividerDrawable = typedArray.getDrawable(0);
            typedArray.recycle();
            setOrientation(orientation);
        }

        private void setOrientation(int orientation) {
            if (orientation != HORIZONTAL && orientation != VERTICAL) {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

}
