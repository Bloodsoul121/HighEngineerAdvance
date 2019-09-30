package com.blood.highengineeradvance.mediaplayer.android;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blood.highengineeradvance.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AndroidMediaPlayerActivity extends AppCompatActivity {

    @BindView(R.id.cur_progress)
    TextView mCurProgress;
    @BindView(R.id.total_progress)
    TextView mTotalProgress;
    @BindView(R.id.seekbar)
    SeekBar mSeekbar;
    @BindView(R.id.play)
    ImageView mPlay;

    private String mMusicPath;
    private boolean mIsFirstPlay;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.reset();
    }

    private void init() {
        mIsFirstPlay = true;
        mMediaPlayer = new MediaPlayer();
        mMusicPath = "file:///android_asset/music/music.mp3";
        mPlay.setActivated(false);
    }

    @OnClick({R.id.play, R.id.pre, R.id.next, R.id.cur_progress, R.id.seekbar, R.id.total_progress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play:
                if (mIsFirstPlay) {
                    mIsFirstPlay = false;
                    play();
                    mPlay.setActivated(true);
                } else {
                    doPlayOrPause();
                }
                break;
            case R.id.pre:
                break;
            case R.id.next:
                break;
        }
    }

    private void play() {
        try {
            mMediaPlayer.reset();//进行重置
            mMediaPlayer.setDataSource(mMusicPath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doPlayOrPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mPlay.setActivated(false);
        } else {
            mMediaPlayer.start();
            mPlay.setActivated(true);
        }
    }

    private String formatTime(int length) {
        Date date = new Date(length);//调用Date方法获值
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);//规定需要形式
        return simpleDateFormat.format(date);
    }

}
