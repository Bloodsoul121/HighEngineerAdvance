package com.blood.highengineeradvance.ui.customview;

import android.content.SyncStatusObserver;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.blood.highengineeradvance.R;

public class CustomViewActivity extends AppCompatActivity {

    private static final String TAG = "CustomViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        testPaint();
    }

    private void testPaint() {
        Paint paint = new Paint();
        int len = paint.breakText("haohaode", 0, 7, true, 100, null);
        Log.i(TAG, "len - " + len);
    }
}
