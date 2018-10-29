package com.blood.highengineeradvance.ui.customview.animate;


import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class KeyFrameView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float progress;

    public KeyFrameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.RED);

        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100);
        Keyframe keyframe3 = Keyframe.ofFloat(1f, 60);

        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, propertyValuesHolder);

        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(3000);

        animator.start();

    }

    public void setProgress(float value) {
        this.progress = value;
        invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(100, 100, 400, 400, -225, progress * 2.7f, false, mPaint);
    }
}
