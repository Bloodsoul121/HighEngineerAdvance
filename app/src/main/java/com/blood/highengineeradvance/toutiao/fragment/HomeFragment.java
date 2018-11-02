package com.blood.highengineeradvance.toutiao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.util.LogUtil;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private LayoutInflater mLayoutInflater;
    private View mFragmentView;
    private HomeAdapter mAdapter;
    private List<Fragment> mDatas;
    private String[] mItemNames = {"关注", "推荐", "热点", "深圳", "视频", "图片", "问答", "娱乐", "科技", "军事", "体育", "直播"};
    private TabPageIndicator mIndicator;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
            mFragmentView = mLayoutInflater.inflate(R.layout.layout_fragment_home, container, false);
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
            mDatas.add(ItemFragment.newInstance(mItemName));
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

}
