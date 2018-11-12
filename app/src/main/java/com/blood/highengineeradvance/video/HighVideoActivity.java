package com.blood.highengineeradvance.video;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blood.highengineeradvance.R;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class HighVideoActivity extends AppCompatActivity {

    // 视频地址
    private static final String url1 = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
    private static final String url2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
    private static final String url3 = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    private static final String url4 = "http://42.96.249.166/live/24035.m3u8";

    private VideoView mVideoView;
    private TextView downloadRateView, loadRateView;
    private ProgressBar pb;
    private CustomMediaController mCustomMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_high_video);
        initView();
        initVitamio();
    }

    private void initView() {
        mVideoView = findViewById(R.id.buffer);
        pb = findViewById(R.id.probar);
        downloadRateView = findViewById(R.id.download_rate);
        loadRateView = findViewById(R.id.load_rate);
    }

    private void initVitamio() {
        //初始化加载库文件
        if (Vitamio.isInitialized(this)) {
            mVideoView.setVideoURI(Uri.parse(url1));
            mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            mCustomMediaController = new CustomMediaController(this, mVideoView, this);
            mCustomMediaController.setVideoName("蓝莲花");
            mCustomMediaController.show(5000); //5s隐藏
            mVideoView.setMediaController(mCustomMediaController);
            mVideoView.setBufferSize(10240); //设置视频缓冲大小
            mVideoView.requestFocus();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
            mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    loadRateView.setText(percent + "%");
                }
            });
            mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        //开始缓冲
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            if (mVideoView.isPlaying()) {
                                mVideoView.pause();
                                pb.setVisibility(View.VISIBLE);
                                downloadRateView.setText("");
                                loadRateView.setText("");
                                downloadRateView.setVisibility(View.VISIBLE);
                                loadRateView.setVisibility(View.VISIBLE);
                            }
                            break;
                        //缓冲结束
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            mVideoView.start();
                            pb.setVisibility(View.GONE);
                            downloadRateView.setVisibility(View.GONE);
                            loadRateView.setVisibility(View.GONE);
                            break;
                        //正在缓冲
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                            downloadRateView.setText("" + extra + "kb/s" + "  ");
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
        if (mVideoView != null){
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

}
