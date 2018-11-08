package com.blood.highengineeradvance.toutiao;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.statusbar.StatusBar;
import com.blood.highengineeradvance.toutiao.fragment.HomeFragment;

public class ToutiaoActivity extends AppCompatActivity {

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
        HomeFragment homeFragment = HomeFragment.newInstance();
        transaction.replace(R.id.container, homeFragment);
        transaction.commit();
    }

}
