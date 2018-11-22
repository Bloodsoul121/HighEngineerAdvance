package com.blood.highengineeradvance.material_design.floating_action_button;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyAdapter;

import java.util.ArrayList;
import java.util.List;

public class FloatingActionButtonActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_action_button);

        init();
    }

    private void init() {
        mRecyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecyAdapter adapter = new RecyAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setData(createDatas());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });
    }

    private List<String> createDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item - " + i);
        }
        return list;
    }

    private static class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {

        private Context mContext;
        private List<String> mDatas;

        private RecyAdapter(Context context) {
            this.mContext = context;
        }

        public void setData(List<String> datas) {
            this.mDatas = datas;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_floating_recyclerview, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyAdapter.ViewHolder viewHolder, int i) {
            viewHolder.mTv.setText(mDatas.get(i));
        }

        @Override
        public int getItemCount() {
            if (mDatas == null) {
                return 0;
            }
            return mDatas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView mTv;

            private ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTv = itemView.findViewById(R.id.tv);
            }
        }
    }
}
