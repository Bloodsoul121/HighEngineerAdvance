package com.blood.highengineeradvance.ui.customview.imitate;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.util.DensityUtils;
import com.blood.highengineeradvance.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XiaomiTestView2 extends View {

    /** 初始圆心位置 X 与 Canvas 宽度之比 **/
    static final float START_CIRCLE_X_SCALE = 0.5f;
    /** 初始圆心位置 Y 与 Canvas 宽度之比 **/
    static final float START_CIRCLE_Y_SCALE = 0.5f;
    /** 圆环半径与 Canvas 宽度之比 **/
    private static final float RADIUS_SCALE = 0.32f;

    /** 线条数目 **/
    private static final int LINE_AMOUNT = 15;
    /** 线条大小 dp **/
    private static final float LINE_SIZE = 0.5f;

    /** 线条半径偏离范围最大值 px（约为圆环宽度 / 2） **/
    private float lineMaxDxy;
    /** 线条圆心偏离范围最大值 px **/
    private float lineMaxDr;
    /** 线条半径偏离范围最大值 dp（越大显得越宽） **/
    private static final float LINE_MAX_DR = 4f;
    /** 线条圆心偏离范围最大值 dp （越大显得越宽）**/
    private static final float LINE_MAX_DC = 4f;

    /** 星星数目 **/
    private static final int STAR_AMOUNT = 30;
    /** 星星大小 dp **/
    private static final float STAR_SIZE = 10f;
    /** 星星逃离 X 轴最大速度 dp **/
    private static final float STAR_MAX_VX = 2.5f;
    /** 星星逃离 Y 轴最大速度 dp **/
    private static final float STAR_MAX_VY = 2.5f;
    /** 星星逃离 X 轴最大速度 px **/
    private float starMaxVx;
    /** 星星逃离 Y 轴最大速度 px **/
    private float starMaxVy;
    /** 星星速度衰减常量 dp **/
    private static final float STAR_DECAY_RATE_CONST = 0.001f;
    /** 星星消失临界距离 dp **/
    private static final float STAR_DISAPPEAR_DISTANCE = 60f;
    /** 星星消失临界亮度 dp **/
    private static final float STAR_DISAPPEAR_ALPHA = 0.05f;

    private Random mRandom;
    private List<LineArgument> mLineArgumentList;
    private int endColor;
    private int startColor;
    private Paint linePaint;
    private int mWidth;
    private int mHeight;
    private boolean needRefresh;
    private int mRadius;
    private float rotateDegree;
    private int mCircleX;
    private int mCircleY;
    private RectF lineRectF;
    private List<StarArgument> mStarArgumentList;
    private Paint starPaint;
    private float angle;
    private float starDecayRateConst;
    private float starDisappearDistance;

    public XiaomiTestView2(Context context, @Nullable AttributeSet attrs) {
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "angle", 0, 360);
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (StarArgument argument : mStarArgumentList) {
                    argument.next();
                }
            }
        });
        animator.start();
    }

    private void setAngle(float angle) {
        this.angle = angle;
        rotateDegree = angle;
        invalidate();
    }

    private float getAngle() {
        return this.angle;
    }

    private void init(Context context) {
        lineRectF = new RectF(0, 0, 0, 0);
        lineMaxDxy = DensityUtils.dp2px(context, LINE_MAX_DR);
        lineMaxDr = DensityUtils.dp2px(context, LINE_MAX_DC);
        mRandom = new Random();

        startColor = ContextCompat.getColor(context, R.color.white);
        endColor = ContextCompat.getColor(context, R.color.transparent);

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeWidth(DensityUtils.dp2px(context, LINE_SIZE));
        linePaint.setAntiAlias(true);

        mLineArgumentList = new ArrayList<>();
        for (int i = 0; i < LINE_AMOUNT; i++) {
            mLineArgumentList.add(new LineArgument());
        }

        float starSize = DensityUtils.dp2px(context, STAR_SIZE);
        starMaxVx = DensityUtils.dp2px(context, STAR_MAX_VX);
        starMaxVy = DensityUtils.dp2px(context, STAR_MAX_VY);

        starDecayRateConst = DensityUtils.dp2px(context, STAR_DECAY_RATE_CONST);
        starDisappearDistance = DensityUtils.dp2px(context, STAR_DISAPPEAR_DISTANCE);

        starPaint = new Paint();
        starPaint.setStrokeCap(Paint.Cap.ROUND);
        starPaint.setStrokeWidth(starSize);

        mStarArgumentList = new ArrayList<>();
        for (int i = 0; i < STAR_AMOUNT; i++) {
            mStarArgumentList.add(new StarArgument());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (canvas.getHeight() != mHeight || canvas.getWidth() != mWidth) {
            needRefresh = true;
            mHeight = canvas.getHeight();
            mWidth = canvas.getWidth();
        }

        if (needRefresh) {
            mCircleX = (int) (mWidth * START_CIRCLE_X_SCALE);
            mCircleY = (int) (mHeight * START_CIRCLE_Y_SCALE);
            SweepGradient lineSweepGradient = new SweepGradient(mCircleX, mCircleY, endColor, startColor);
            linePaint.setShader(lineSweepGradient);

            mRadius = (int) (canvas.getWidth() * RADIUS_SCALE);

            needRefresh = false;

            // 星星需要宽度才能启动
            for(StarArgument argument : mStarArgumentList) {
                argument.reset();
            }
        }

        canvas.save();

        canvas.rotate(rotateDegree, mCircleX, mCircleY);
        for (LineArgument argument : mLineArgumentList) {
            float dx = argument.dx;
            float dy = argument.dy;
            float dr = argument.dr;

            LogUtil.i("LineArgument " + dx + " / " + dy + " / " + dr);

            lineRectF.set(mCircleX - mRadius - dx - dr,
                    mCircleY - mRadius - dy - dr,
                    mCircleX + mRadius + dr + dx,
                    mCircleY + mRadius + dr + dy);
            canvas.drawArc(lineRectF, 15, 345, false, linePaint);
        }

        for (StarArgument argument : mStarArgumentList) {
            float dx = argument.dx;
            float dy = argument.dy;
            int alphaMask = ((int) (argument.alpha * 0xff)) << 24 ;
            int transparentColor = (startColor & 0x00ffffff) + alphaMask;
            starPaint.setColor(transparentColor);
            canvas.drawPoint(mCircleX + mRadius + dx, mCircleY + dy, starPaint);
        }
        starPaint.setColor(startColor);
        canvas.drawPoint(mCircleX + mRadius, mCircleY, starPaint);

        canvas.restore();
    }

    private class LineArgument {
        /** 圆心 X 轴偏移值 **/
        float dx;
        /** 圆心 Y 轴偏移值 **/
        float dy;
        /** 圆半径 r 偏移值 **/
        float dr;

        private LineArgument() {
            dx = nextSignedFloat() * lineMaxDr;
            dy = nextSignedFloat() * lineMaxDr;
            dr = nextSignedFloat() * lineMaxDxy;
        }
    }

    private float nextSignedFloat() {
        return (mRandom.nextBoolean() ? 1 : -1) * mRandom.nextFloat();
    }

    private class StarArgument {
        /** 距离源点 X 轴偏移 **/
        float dx;
        /** 距离源点 Y 轴偏移 **/
        float dy;
        /** 星星透明度 **/
        float alpha;

        /** 逃离源点 X 轴速度 **/
        float vx;
        /** 逃离源点 Y 轴速度 **/
        float vy;
        /** 逃离源点 X 轴加速度 **/
        double ax;
        /** 逃离源点 Y 轴加速度 **/
        double ay;

        private StarArgument() {}

        private void reset() {
            dx = 0;
            dy = 0;
            alpha = 1;
            vx = nextSignedFloat() * starMaxVx;
            vy = mRandom.nextFloat() * starMaxVy + -2 * (float)Math.PI * mWidth * RADIUS_SCALE * ((float)3 / 360);
            ax = starDecayRateConst;
            ay = starDecayRateConst;
        }

        private void next() {
            dx += vx / 2;
            vx += ax;
            dx += vx / 2;

            dy += vy / 2;
            vy += ay;
            dy += vy / 2;

            // 越远越暗
            alpha = 1 - (float) Math.sqrt(dx * dx + dy * dy) / starDisappearDistance;

            if (alpha < STAR_DISAPPEAR_ALPHA) {
                reset();
            }
        }

    }

}
