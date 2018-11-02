package com.blood.highengineeradvance.toutiao.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.toutiao.recyclerview.BaseRecyViewHolder;
import com.blood.highengineeradvance.toutiao.recyclerview.PageRecyAdapter;
import com.blood.highengineeradvance.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsAdapter mAdapter;

    public static ItemFragment newInstance(String name) {
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_fragment_home_item, container, false);
            initView(mView);
        }
        return mView;
    }

    private void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new NewsAdapter(getContext());
        mAdapter.bindRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                runOnThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                                mAdapter.addData(createData(), 0);
                            }
                        });
                    }
                });
            }
        });
        mAdapter.setOnLoadMoreListener(new PageRecyAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                runOnThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.addLoadMoreData(createData());
                            }
                        });
                    }
                });
            }
        });
        mAdapter.openAutoLoadMore();
        mAdapter.startLoadMore();
        mAdapter.setData(createData());
    }

    private void runOnThread(Runnable r) {
        new Thread(r).start();
    }

    private void runOnUiThread(Runnable r) {
        getActivity().runOnUiThread(r);
    }

    private List<String> createData() {
        List<String> datas = new ArrayList<>();
        datas.add("李白 青莲剑歌");
        datas.add("赵云 枪出于龙");
        datas.add("赵云 于万军丛中取敌将首级");
        datas.add("花木兰 谁会是下一条龙");
        datas.add("妲己 让我们来玩耍吧");
        datas.add("伤心不是哭的理由,傻才是");
        datas.add("心怀不惧，方能翱翔于天际。");
        datas.add("努力有用的话还要天才干什么。");
        datas.add("一篇诗，一斗酒，一曲长歌，一剑天涯。");
        datas.add("逆了苍天，踏破碧落黄泉。");
        datas.add("风会带走你曾经存在过的证明。");
        return datas;
    }

    private class NewsAdapter extends PageRecyAdapter<String> {

        public NewsAdapter(Context context) {
            super(context);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.layout_recy_item_news;
        }

        @Override
        protected void onBindItemViewHolder(BaseRecyViewHolder holder, String s, int position) {
            holder.setText(R.id.tv_news, s);
        }

        @Override
        public View inflateLoadingView() {
            return createView("加载中...");
        }

        @Override
        public View inflateLoadFailedView() {
            return createView("加载失败");
        }

        @Override
        public View inflateLoadEndView() {
            return createView("已加载完全部资讯");
        }

        private TextView createView(String text) {
            TextView tv = new TextView(mContext);
            tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(10,30,10,30);
            tv.setText(text);
            return tv;
        }
    }
}
