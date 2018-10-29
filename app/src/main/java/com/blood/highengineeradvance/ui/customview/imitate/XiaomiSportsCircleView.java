package com.blood.highengineeradvance.ui.customview.imitate;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.util.LogUtil;

public class XiaomiSportsCircleView extends View {

    private static final int LINE_COUNT = 8;
    private static final int LINE_CENTER_OFFSET = 10;
    private Paint mStartLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mParticlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private SweepGradient mSweepGradient;
    private float mAngle;
    private float mCenterX = 400;
    private float mCenterY = 300;
    private float mRadius = 200;
    private float[] mCenterOffsetsX;
    private float[] mCenterOffsetsY;

    /** View的宽高 **/
    private int mWidth;
    private int mHeight;
    private Bitmap mBackgroundBitmap;

    public XiaomiSportsCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startRotation();
            }
        });
    }

    private void init() {
        mStartLinePaint.setStyle(Paint.Style.STROKE);
        mStartLinePaint.setStrokeWidth(2);

        mSweepGradient = new SweepGradient(mCenterX, mCenterY, Color.parseColor("#00000000"), Color.parseColor("#ffffff"));

        mStartLinePaint.setShader(mSweepGradient);

        mCenterOffsetsX = new float[LINE_COUNT];
        mCenterOffsetsY = new float[LINE_COUNT];
        for (int i = 0; i < LINE_COUNT; i++) {
            mCenterOffsetsX[i] = (float) (Math.random() * 2 - 1);
            mCenterOffsetsY[i] = (float) (Math.random() * 2 - 1);
        }

        mParticlePaint.setStyle(Paint.Style.FILL);

        // 背景图
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_step_law);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackgroundBitmap(canvas);

        canvas.save();

        canvas.rotate(mAngle, mCenterX, mCenterY);
        for (int i = 0; i < LINE_COUNT; i++) {
            float realCenterX = mCenterX + LINE_CENTER_OFFSET * mCenterOffsetsX[i];
            float realCenterY = mCenterY + LINE_CENTER_OFFSET * mCenterOffsetsY[i];
            canvas.drawCircle(realCenterX, realCenterY, mRadius, mStartLinePaint);
        }

        float particleX = mCenterX + mRadius;
        float particleY = mCenterY;

//        canvas.drawCircle();

        canvas.restore();

    }

    private void drawBackgroundBitmap(Canvas canvas) {
        float scaleX = (float) mWidth / mBackgroundBitmap.getWidth();
        float scaleY = (float) mHeight / mBackgroundBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        mBackgroundBitmap = Bitmap.createBitmap(mBackgroundBitmap, 0, 0, mBackgroundBitmap.getWidth(), mBackgroundBitmap.getHeight(), matrix, true);
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
    }

    private void startRotation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "angle", 0, 720);
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void setAngle(float angle) {
        this.mAngle = angle;
        invalidate();
    }

    private float getAngle() {
        return this.mAngle;
    }

}
