package com.blood.highengineeradvance.video.ijk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.video.ijk.other1.Ijkplayer;
import com.blood.highengineeradvance.video.ijk.other1.VideoPlayerListener;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkOther1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other1);
        init();
    }

    private void init() {
        Ijkplayer ijkplayer = findViewById(R.id.ijkplayer);
        //加载native库
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            this.finish();
        }
        ijkplayer.setListener(new VideoPlayerListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            }

            @Override
            public void onCompletion(IMediaPlayer mp) {
            }

            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                return false;
            }

            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                return false;
            }

            @Override
            public void onPrepared(IMediaPlayer mp) {
                // 视频准备好播放了，但是他不会自动播放，需要手动让他开始。
                mp.start();
            }

            @Override
            public void onSeekComplete(IMediaPlayer mp) {

            }

            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                //在此可以获取到视频的宽和高
            }
        });
        ijkplayer.setVideoPath("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4");
    }

    @Override
    protected void onStop() {
        super.onStop();
        IjkMediaPlayer.native_profileEnd();
    }
}
