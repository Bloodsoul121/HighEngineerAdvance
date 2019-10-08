package com.blood.highengineeradvance.mediaplayer.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blood.highengineeradvance.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AndroidMPActivity extends AppCompatActivity {

    @BindView(R.id.cur_progress)
    TextView mCurProgress;
    @BindView(R.id.total_progress)
    TextView mTotalProgress;
    @BindView(R.id.seekbar)
    SeekBar mSeekbar;
    @BindView(R.id.play)
    ImageView mPlay;

    private AndroidMPPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        ButterKnife.bind(this);
        mPresenter = new AndroidMPPresenter(this, this);
        mPresenter.init();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        mCurProgress.setText("00:00");
        mTotalProgress.setText("00:00");
        mPlay.setActivated(false);
    }

    @OnClick({R.id.play, R.id.pre, R.id.next, R.id.cur_progress, R.id.seekbar, R.id.total_progress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play:
                mPresenter.playOrPause();
                break;
            case R.id.pre:
                break;
            case R.id.next:
                break;
        }
    }

    public void onPlayOrPause(boolean isPlay) {
        mPlay.setActivated(isPlay);
    }

    public void onMediaProgressChanged(int progress, int totalTime) {
        mSeekbar.setMax(totalTime);
        mSeekbar.setProgress(progress);
        mCurProgress.setText(formatTime(progress));
        mTotalProgress.setText(formatTime(totalTime));
    }

    private String formatTime(int length) {
        Date date = new Date(length);//调用Date方法获值
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);//规定需要形式
        return simpleDateFormat.format(date);
    }
}
