package com.blood.highengineeradvance.toutiao.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.util.LogUtil;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class XItemFragment extends Fragment {

    private View mView;
    private List<String> mDatas;
    private XNewsAdapter mAdapter;
    private XRecyclerView mXRecyclerView;

    public static XItemFragment newInstance(String name) {
        XItemFragment fragment = new XItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_fragment_home_xitem, container, false);
            initView(mView);
        }
        return mView;
    }

    private void initView(View view) {
        mXRecyclerView = view.findViewById(R.id.xrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new XNewsAdapter();
        mXRecyclerView.setAdapter(mAdapter);
        initXRecyclerView();
        initData();
        mAdapter.notifyDataSetChanged();
    }

    private void initXRecyclerView() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                LogUtil.i("onRefresh " + Thread.currentThread().getName());
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
                                mDatas.addAll(0, createData());
                                mAdapter.notifyDataSetChanged();
                                mXRecyclerView.refreshComplete();
                            }
                        });
                    }
                });
            }

            @Override
            public void onLoadMore() {
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
                                mDatas.addAll(createData());
                                mAdapter.notifyDataSetChanged();
                                mXRecyclerView.loadMoreComplete();
                            }
                        });
                    }
                });
            }
        });
        mXRecyclerView.setLimitNumberToCallLoadMore(2); // default is 1
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
    }

    private void runOnThread(Runnable r) {
        new Thread(r).start();
    }

    private void runOnUiThread(Runnable r) {
        getActivity().runOnUiThread(r);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("李白 青莲剑歌");
        mDatas.add("赵云 枪出于龙");
        mDatas.add("赵云 于万军丛中取敌将首级");
        mDatas.add("花木兰 谁会是下一条龙");
        mDatas.add("妲己 让我们来玩耍吧");
        mDatas.add("伤心不是哭的理由,傻才是");
        mDatas.add("心怀不惧，方能翱翔于天际。");
        mDatas.add("努力有用的话还要天才干什么。");
        mDatas.add("一篇诗，一斗酒，一曲长歌，一剑天涯。");
        mDatas.add("逆了苍天，踏破碧落黄泉。");
        mDatas.add("风会带走你曾经存在过的证明。");
    }

    private List<String> createData() {
        List<String> datas = new ArrayList<>();
        int random = (int) (System.currentTimeMillis() % 3);
        if (random == 0) {
            datas.add("伤心不是哭的理由,傻才是");
            datas.add("飞舞战场的美少女，大活跃Q！——蔡文姬 ");
            datas.add("光明，制造瞎子。——后羿 ");
            datas.add("逆了苍天，踏破碧落黄泉。");
            datas.add("心怀不惧，方能翱翔于天际。");
            datas.add("生命就像人家的魔法书，涂涂改改又是一年。——安琪拉 ");
        } else if (random == 1) {
            datas.add("妲己 让我们来玩耍吧");
            datas.add("赵云 枪出于龙");
            datas.add("努力有用的话还要天才干什么。");
            datas.add("赵云 于万军丛中取敌将首级");
            datas.add("宁教我负天下人！力量也会臣服于我！——曹操 ");
            datas.add("无敌的我，又迷路了。——宫本武藏  ");
        } else {
            datas.add("一篇诗，一斗酒，一曲长歌，一剑天涯。");
            datas.add("风会带走你曾经存在过的证明。");
            datas.add("一个跟头十万八千里，俺老孙也是风一般的男子。——孙悟空 ");
            datas.add("和我生在同一个时代，真是你的不幸。——周瑜 ");
            datas.add("剑的意义，在于守护想要守护的人。——宫本武藏 ");
            datas.add("可以赶尽，无法杀绝。——李白 ");
        }
        return datas;
    }

    private class XNewsAdapter extends RecyclerView.Adapter<XNewsAdapter.XNewsViewHolder> {

        @Override
        public XNewsAdapter.XNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_xrecy_item_news, parent, false);
            return new XNewsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(XNewsAdapter.XNewsViewHolder holder, int position) {
            holder.mTextView.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            if (mDatas == null) {
                return 0;
            }
            return mDatas.size();
        }

        public class XNewsViewHolder extends RecyclerView.ViewHolder {

            private TextView mTextView;

            private XNewsViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.tv_news);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView = null;
        if(mXRecyclerView != null){
            mXRecyclerView.destroy(); // this will totally release XR's memory
            mXRecyclerView = null;
        }
    }

}
