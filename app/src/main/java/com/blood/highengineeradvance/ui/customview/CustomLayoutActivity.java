package com.blood.highengineeradvance.ui.customview;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.util.LogUtil;

public class CustomLayoutActivity extends AppCompatActivity {

    private AppCompatSeekBar mSeekBar1;
    private AppCompatSeekBar mSeekBar2;
    private RelativeLayout mContainer;
    private LinearLayout mParentLayout;
    float bottomMargin = dpToPixel(48);
    float minWidth = dpToPixel(80);
    float minHeight = dpToPixel(100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout);

        mContainer = findViewById(R.id.container);
        mParentLayout = findViewById(R.id.parentlayout);
        mSeekBar1 = findViewById(R.id.seekbar1);
        mSeekBar2 = findViewById(R.id.seekbar2);

        init();
    }

    private void init() {
        mSeekBar1.setOnSeekBarChangeListener(listener);
        mSeekBar2.setOnSeekBarChangeListener(listener);
    }

    private SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mParentLayout.getLayoutParams();
            layoutParams.width = (int) (minWidth + (mContainer.getWidth() - minWidth) * mSeekBar1.getProgress() / 100);
            layoutParams.height = (int) (minHeight + (mContainer.getHeight() - minHeight) * mSeekBar2.getProgress() / 100);
            LogUtil.i("onProgressChanged " + layoutParams.width + " / " + layoutParams.height);
            mParentLayout.setLayoutParams(layoutParams);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }
}
