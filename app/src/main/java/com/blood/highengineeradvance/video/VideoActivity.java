package com.blood.highengineeradvance.video;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blood.highengineeradvance.R;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private String url1 = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
    private String url2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
    private String url3 = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    private String url4 = "http://42.96.249.166/live/388.m3u8";
    private String url5 = "http://live.3gv.ifeng.com/zixun.m3u8";

    private VideoView mVideoView;
    private TextView percentTv;
    private TextView netSpeedTv;
    private ImageView mImgPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
    }

    private void initView() {
        // 视频
        mVideoView = findViewById(R.id.videoview);
        // 显示缓冲百分比的TextView
        percentTv = findViewById(R.id.buffer_percent);
        // 显示下载网速的TextView
        netSpeedTv = findViewById(R.id.net_speed);
        // 播放按钮
        mImgPlay = findViewById(R.id.img_play);
        mImgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgPlay.setVisibility(View.GONE);
                initVideo();
            }
        });
    }

    private void initVideo() {
        if (!Vitamio.isInitialized(this)) {
            return;
        }
        // 视频网址
        mVideoView.setVideoURI(Uri.parse(url1));
        // 视频质量
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        // 设置媒体控制器
        MediaController mediaController = new MediaController(this);
        mVideoView.setMediaController(mediaController);
        // 设置视频缓冲大小。默认1024KB，单位byte
        mVideoView.setBufferSize(10 * 1024);
        // 获取焦点
        mVideoView.requestFocus();
        // 预处理完成监听回调，准备播放监听
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);//设置播放速度
            }
        });
        // 在网络视频流缓冲变化时调用
        mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                percentTv.setText("已缓冲：" + percent + "%");
            }
        });
        // 在有警告或错误信息时调用。例如：开始缓冲、缓冲结束、下载速度变化。
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    //开始缓冲
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        percentTv.setVisibility(View.VISIBLE);
                        netSpeedTv.setVisibility(View.VISIBLE);
                        mp.pause();
                        break;
                    //缓冲结束
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        percentTv.setVisibility(View.GONE);
                        netSpeedTv.setVisibility(View.GONE);
                        mp.start(); //缓冲结束再播放
                        break;
                    //正在缓冲
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        netSpeedTv.setText("当前网速:" + extra + "kb/s");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.pause();
        mVideoView = null;
    }
}
