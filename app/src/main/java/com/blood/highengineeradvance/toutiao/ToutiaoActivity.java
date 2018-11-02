package com.blood.highengineeradvance.toutiao;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.statusbar.StatusBar;
import com.blood.highengineeradvance.toutiao.fragment.HomeFragment;
import com.blood.highengineeradvance.toutiao.view.CustomPageTextView;

public class ToutiaoActivity extends AppCompatActivity {

    private CustomPageTextView mCustomPageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setStatusBarColor(this, Color.parseColor("#d43d3d"));
        setContentView(R.layout.activity_toutiao);
        init();
    }

    private void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.container, homeFragment);
        transaction.commit();

        mCustomPageTextView = findViewById(R.id.search_bar);
        mCustomPageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomPageTextView.setTextByPage("我的李白天下无敌");
            }
        });

    }

}
