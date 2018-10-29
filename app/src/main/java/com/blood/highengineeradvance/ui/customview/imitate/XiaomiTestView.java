package com.blood.highengineeradvance.ui.customview.imitate;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.util.DensityUtils;

public class XiaomiTestView extends View {

    /** 初始圆心位置 X 与 Canvas 宽度之比 **/
    static final float START_CIRCLE_X_SCALE = 0.5f;
    /** 初始圆心位置 Y 与 Canvas 宽度之比 **/
    static final float START_CIRCLE_Y_SCALE = 0.5f;


    /** 圆半径控制变量 scale **/
    private float circleRadiusIncrement = 0;
    /** 圆环画笔 **/
    private Paint bigCirclePaint;
    /** 圆环半径与 Canvas 宽度之比 **/
    private static final float BIG_CIRCLE_RADIUS_SCALE = 0.38f;
    /** 圆环粗细大小 dp （画笔大小）**/
    private static final int BIG_CIRCLE_SIZE = 16;
    /** 圆环抖动效果半径 dp **/
    private static final float BIG_CIRCLE_SHAKE_RADIUS = 20;
    /** 圆环抖动效果偏移 dp **/
    private static final float BIG_CIRCLE_SHAKE_OFFSET = 0.4f;
    /** 光晕画笔 **/
    private Paint blurPaint;
    /** 圆环光晕效果层数 **/
    private static final int CIRCLE_BLUR_LAYER_AMOUNT = 4;
    /** 圆环光晕效果大小 dp **/
    private static final float CIRCLE_BLUR_SIZE = 16;

    private float circleX;
    private float circleY;
    private float mBigCircleRadius;
    private float mCircleAlphaProgress = 0.5f;
    private RectF blurOvalRectF = new RectF();
    private float blurSize = 0;
    private int mCircleColor;
    private float mDegree;
    private FireworksCircleGraphics mFireworksCircleGraphics;

    public XiaomiTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "degree", 0, 360);
        animator.setDuration(5000);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void setDegree(float degree) {
        this.mDegree = degree;
        invalidate();
    }

    private float getDegree() {
        return mDegree;
    }

    private void init(Context context) {
        bigCirclePaint = new Paint();
        bigCirclePaint.setStrokeWidth(DensityUtils.dp2px(context, BIG_CIRCLE_SIZE));
        bigCirclePaint.setStyle(Paint.Style.STROKE);
        bigCirclePaint.setAntiAlias(true);

        blurPaint = new Paint(bigCirclePaint);
        blurSize = DensityUtils.dp2px(context, CIRCLE_BLUR_SIZE);

        mCircleColor = ContextCompat.getColor(context, R.color.whiteTransparent);

        mFireworksCircleGraphics = new FireworksCircleGraphics(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        circleX = width * START_CIRCLE_X_SCALE;
        circleY = height * START_CIRCLE_Y_SCALE;
        drawOverCircle(canvas);

        mFireworksCircleGraphics.draw(canvas);
    }

    private void drawOverCircle(Canvas canvas) {
        mBigCircleRadius = canvas.getWidth() * BIG_CIRCLE_RADIUS_SCALE;
        mCircleAlphaProgress = 1f;
        canvas.save();
        canvas.scale(1 + circleRadiusIncrement / BIG_CIRCLE_RADIUS_SCALE, 1 + circleRadiusIncrement / BIG_CIRCLE_RADIUS_SCALE, circleX, circleY);
        canvas.rotate(mDegree, circleX, circleY);
        drawBigCircle(canvas);
        drawBlur(canvas);
        canvas.restore();
    }

    private void drawBlur(Canvas canvas) {
        Shader blurLinearGradient = new LinearGradient(
                circleX, circleY,
                circleX + mBigCircleRadius, circleY,
                ContextCompat.getColor(getContext(), R.color.transparent),
                ContextCompat.getColor(getContext(), R.color.white),
                Shader.TileMode.CLAMP);
        blurPaint.setShader(blurLinearGradient);

        // 光晕
        blurPaint.setAlpha((int) (Color.alpha(mCircleColor) * mCircleAlphaProgress));
        for (int i = 0; i < CIRCLE_BLUR_LAYER_AMOUNT; i++) {
            blurPaint.setAlpha(0xff * (CIRCLE_BLUR_LAYER_AMOUNT - i) / (CIRCLE_BLUR_LAYER_AMOUNT * 3));
            blurOvalRectF.set(circleX - mBigCircleRadius, circleY - mBigCircleRadius,
                    circleX + mBigCircleRadius + i * blurSize / CIRCLE_BLUR_LAYER_AMOUNT, circleY + mBigCircleRadius);
            canvas.drawOval(blurOvalRectF, blurPaint);
        }
    }

    private void drawBigCircle(Canvas canvas) {
        Shader bigCircleLinearGradient = new LinearGradient(
                circleX - mBigCircleRadius, circleY,
                circleX + mBigCircleRadius, circleY,
                ContextCompat.getColor(getContext(), R.color.whiteTransparent),
                ContextCompat.getColor(getContext(), R.color.white),
                Shader.TileMode.CLAMP);
        bigCirclePaint.setShader(bigCircleLinearGradient);

        PathEffect pathEffect1 = new CornerPathEffect(DensityUtils.dp2px(getContext(), BIG_CIRCLE_SHAKE_RADIUS));
        PathEffect pathEffect2 = new DiscretePathEffect(DensityUtils.dp2px(getContext(), BIG_CIRCLE_SHAKE_RADIUS),
                DensityUtils.dp2px(getContext(), BIG_CIRCLE_SHAKE_OFFSET));
        PathEffect pathEffect = new ComposePathEffect(pathEffect1, pathEffect2);
        bigCirclePaint.setPathEffect(pathEffect);

        bigCirclePaint.setAlpha((int) (0xff * mCircleAlphaProgress));
        canvas.drawCircle(circleX, circleY, mBigCircleRadius, bigCirclePaint);
    }
}
