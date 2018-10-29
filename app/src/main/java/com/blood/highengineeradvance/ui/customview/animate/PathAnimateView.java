package com.blood.highengineeradvance.ui.customview.animate;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;

public class PathAnimateView extends View {

    private float progress;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PathAnimateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 65);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);

        Path path = new Path();
        // 先以「动画完成度 : 时间完成度 = 1 : 1」的速度匀速运行 25%
        path.lineTo(0.25f, 0.25f);
        // 然后瞬间跳跃到 150% 的动画完成度
        path.moveTo(0.25f, 1.5f);
        // 再匀速倒车，返回到目标点
        path.lineTo(1, 1);

        PathInterpolator pathInterpolator = new PathInterpolator(path);

        animator.setInterpolator(pathInterpolator);

        animator.start();
    }

    public void setProgress(float value) {
        this.progress = value;
        invalidate();
    }

    public float getProgress() {
        return this.progress;
    }

    private Paint mPaint = new Paint();

    private RectF rectF = new RectF(20, 20, 400, 400);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

            canvas.drawArc(rectF, 135, progress * 2.7f, false, mPaint);
    }
}
