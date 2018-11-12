package com.blood.highengineeradvance.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.blood.highengineeradvance.R;

public class WebviewActivity extends AppCompatActivity {

    public static final String WEBVIEW_URL = "webview_url";

    private MyWebview mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_webview);
        initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initView() {
        mWebView = findViewById(R.id.webview);

        mWebView.initWebSetting();
        mWebView.initClient();

        mWebView.loadUrl("file:///android_asset/web/WalletApp.html");

//        mWebView.loadUrl("https://www.baidu.com");
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
