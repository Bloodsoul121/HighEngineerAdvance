package com.blood.highengineeradvance.toutiao.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.toutiao.view.CustomPageTextView;
import com.blood.highengineeradvance.util.KeyboardUtils;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mFragmentView;
    private HomeAdapter mAdapter;
    private List<Fragment> mDatas;
    private String[] mItemNames = {"关注", "推荐", "热点", "深圳", "视频", "图片", "问答", "娱乐", "科技", "军事", "体育", "直播"};
    private TabPageIndicator mIndicator;
    private CustomPageTextView mCustomPageTextView;
    private Group mGroupSearchBar;
    private Group mGroupSearchEdit;
    private EditText mSearchEditText;
    private TextView mSearchResultTextView;
    private Group mGroupNews;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", "home");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(R.layout.layout_fragment_home, container, false);
            initTitleBar(mFragmentView);
            initFragmentView(mFragmentView);
        }
        return mFragmentView;
    }

    private void initFragmentView(View fragmentView) {
        mIndicator = fragmentView.findViewById(R.id.indicator);
        ViewPager viewPager = fragmentView.findViewById(R.id.viewpager);
        mAdapter = new HomeAdapter(getFragmentManager());
        viewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(viewPager);
        initListener(mIndicator);
        initData();
    }

    private void initListener(TabPageIndicator indicator) {
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator.setOnTabReselectedListener(new TabPageIndicator.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (String mItemName : mItemNames) {
//            mDatas.add(ItemFragment.newInstance(mItemName)); // SwipeRefreshLayout
            mDatas.add(XItemFragment.newInstance(mItemName)); // XRecyclerView
        }
        mAdapter.notifyDataSetChanged();
        mIndicator.notifyDataSetChanged(); // indicator 也需要刷新
    }

    private class HomeAdapter extends FragmentPagerAdapter {

        private HomeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (mDatas == null) {
                return null;
            }
            return mDatas.get(position);
        }

        @Override
        public int getCount() {
            if (mDatas == null) {
                return 0;
            }
            return mDatas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mItemNames[position];
        }
    }

    private void initTitleBar(View view) {
        mGroupSearchBar = view.findViewById(R.id.group_search_bar);
        mGroupSearchEdit = view.findViewById(R.id.group_search_edit);

        mCustomPageTextView = view.findViewById(R.id.search_bar);
        mCustomPageTextView.setOnClickListener(this);

        ImageView imgBack = view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);

        mSearchEditText = view.findViewById(R.id.search_edit_text);
        mSearchEditText.setFocusable(true);
        mSearchEditText.setFocusableInTouchMode(true);
        mSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    KeyboardUtils.showSoftInput(mSearchEditText);
                } else {
                    KeyboardUtils.hideSoftInput(mSearchEditText);
                }
            }
        });

        mSearchResultTextView = view.findViewById(R.id.search_result);

        mGroupNews = view.findViewById(R.id.group_news);

        startDynamicPage();
    }

    private void startDynamicPage() {
        runOnThread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long random = System.currentTimeMillis() % 2;
                            if (random == 0) {
                                mCustomPageTextView.setTextByPage("我的李白天下无敌");
                            } else {
                                mCustomPageTextView.setTextByPage("等我解除封印,伤害爆表");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_bar:
                mGroupSearchBar.setVisibility(View.GONE);
                mGroupSearchEdit.setVisibility(View.VISIBLE);
                mGroupNews.setVisibility(View.GONE);
                mSearchResultTextView.setVisibility(View.VISIBLE);
                KeyboardUtils.showSoftInput(mSearchEditText);
                break;
            case R.id.img_back:
                mGroupSearchEdit.setVisibility(View.GONE);
                mGroupSearchBar.setVisibility(View.VISIBLE);
                mSearchResultTextView.setVisibility(View.GONE);
                mGroupNews.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void runOnThread(Runnable r) {
        new Thread(r).start();
    }

    private void runOnUiThread(Runnable r) {
        getActivity().runOnUiThread(r);
    }

}
