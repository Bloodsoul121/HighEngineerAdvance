package com.blood.highengineeradvance.ui.customview.animate;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PointView extends View {

    private AnimatorSet mAnimatorSet;

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

    }

    private void startAnimator() {
        ValueAnimator animator = ObjectAnimator.ofObject(new PointEvaluator(), new Point(radius, radius), new Point(getWidth() - radius, getHeight() - radius));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);

        ValueAnimator animator2 = ObjectAnimator.ofObject(new ColorEvaluator(), "#0000FF", "#FF0000");
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPaint.setColor(Color.parseColor((String) animation.getAnimatedValue()));
            }
        });
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatMode(ValueAnimator.RESTART);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animator, animator2);
        mAnimatorSet.setDuration(3000);

        mAnimatorSet.start();
    }

    private Paint mPaint = new Paint();

    private int radius = 50;

    private Point mPoint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPoint == null) {
            canvas.drawCircle(radius, radius, radius, mPaint);
            startAnimator();
        } else {
            canvas.drawCircle(mPoint.getX(), mPoint.getY(), radius, mPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimatorSet.end();
    }
}
