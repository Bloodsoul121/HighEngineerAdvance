package com.blood.highengineeradvance.ui.customview.imitate;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.blood.highengineeradvance.R;

public class FlipboardView extends View {

    private Camera mCamera;
    private float progress;

    public FlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 1);
        animator.setDuration(1500);
        animator.start();
    }

    private void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    private float getProgress() {
        return progress;
    }

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.google_map);

        mCamera = new Camera();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        mCamera.setLocation(0, 0, newZ);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        canvas.save();
        canvas.clipRect(0, 0, centerX, height);
        canvas.drawBitmap(mBitmap, centerX - mBitmap.getWidth() / 2, centerY - mBitmap.getHeight() / 2, mPaint);
        canvas.restore();

        canvas.save();
        canvas.clipRect(centerX, 0, width, height);
        mCamera.save();
        mCamera.rotateY(-45);
        canvas.translate(centerX, centerY);
        mCamera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        mCamera.restore();
        canvas.drawBitmap(mBitmap, centerX - mBitmap.getWidth() / 2, centerY - mBitmap.getHeight() / 2, mPaint);
        canvas.restore();

    }
}
