package com.blood.highengineeradvance.mediaplayer.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AndroidMPPresenter implements MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private Context mContext;
    private AndroidMPActivity mView;
    private boolean mIsFirstPlay;
    private boolean mIsRunning;
    private Timer mTimer;
    private MediaPlayer mMediaPlayer;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    refreshProgress();
                    break;
            }
        }
    };

    public AndroidMPPresenter(Context context, AndroidMPActivity view) {
        mContext = context;
        mView = view;
    }

    public void init() {
        mIsFirstPlay = true;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnSeekCompleteListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
    }

    public void onDestroy() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mTimer.cancel();
        mIsRunning = false;
    }

    public void playOrPause() {
        if (mIsFirstPlay) {
            mIsFirstPlay = false;
            play();
            mView.onPlayOrPause(true);
        } else {
            doPlayOrPause();
        }
    }

    private void play() {
        try {
            mMediaPlayer.reset();//进行重置
//            mMediaPlayer.setDataSource(mMusicPath);
            AssetFileDescriptor fd = mContext.getAssets().openFd("music.mp3");
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mIsRunning = true;
            startProgressThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void startProgressThread() {
        mIsRunning = true;
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!mIsRunning) {
                    return;
                }
                mHandler.sendEmptyMessage(0);
            }
        }, 100, 800);
    }

    private void doPlayOrPause() {
        if (mMediaPlayer.isPlaying()) {
            pause();
            mView.onPlayOrPause(false);
        } else {
            mIsRunning = true;
            mMediaPlayer.start();
            mView.onPlayOrPause(true);
        }
    }

    public void pause() {
        mIsRunning = false;
        mMediaPlayer.pause();
    }

    public void stop() {
        mIsRunning = false;
        mMediaPlayer.stop();
    }

    private void refreshProgress() {
        int totalTime = mMediaPlayer.getDuration();
        int progress = mMediaPlayer.getCurrentPosition();
        mView.onMediaProgressChanged(progress, totalTime);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
