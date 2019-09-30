package com.blood.highengineeradvance.mediaplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.mediaplayer.android.AndroidMediaPlayerActivity;

public class MediaPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_plater);
    }

    public void clickBtn1(View view) {
        startActivity(new Intent(this, AndroidMediaPlayerActivity.class));
    }

    public void clickBtn2(View view) {

    }
}
