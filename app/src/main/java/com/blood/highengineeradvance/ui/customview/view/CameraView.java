package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.R;

public class CameraView extends View {

    private final Bitmap mBitmap1;

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        centerX = mBitmap1.getWidth() / 2;
        centerY = mBitmap1.getHeight() / 2;
    }

    private Camera mCamera = new Camera();

    private Paint mPaint = new Paint();

    private float centerX = 400;
    private float centerY = 400;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, 0, 400, 0, mPaint);
        canvas.drawLine(0, 0, 0, 400, mPaint);

        canvas.save();

        mCamera.save();
        mCamera.rotateX(30);

        canvas.translate(centerX, centerY); // 旋转之后把投影移动回来

        mCamera.applyToCanvas(canvas);

        canvas.translate(-centerX, -centerY); // 旋转之前把绘制内容移动到轴心（原点）

        mCamera.restore();

        canvas.drawBitmap(mBitmap1, 0, 0, mPaint);

        canvas.restore();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }
}
