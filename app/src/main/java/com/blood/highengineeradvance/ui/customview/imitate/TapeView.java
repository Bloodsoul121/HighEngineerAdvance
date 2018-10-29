package com.blood.highengineeradvance.ui.customview.imitate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

public class TapeView extends View {

    private static final String KG = "kg";
    private Paint mPaint1 = new Paint();
    private Paint mPaint2 = new Paint();
    private Paint mPaint3 = new Paint();
    private Paint mPaint4 = new Paint();
    private String mWeight;
    private float mWeightX;
    private float mWeightY;
    private float mKgX;
    private float mKgY;
    private float num = 0.0f;
    private float mNumWidth;
    private float mNumHeight;
    private float mKgWidth;
    private float mKgHeight;
    private static final float UNIT = 30;
    private static final float DIAL_SHORT = 50;
    private static final float DIAL_LONG = 100;
    private static final float DIAL_CENTER = 110;
    private int mBaseDialY;
    private float offset = 0f;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int maximumFlingVelocity;
    private int minimumFlingVelocity;

    public TapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint1.setTextSize(100);
        mPaint1.setColor(Color.GREEN);
        mPaint1.setStrokeWidth(8);
        mPaint1.setAntiAlias(true);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);
        mPaint2.setTextSize(40);
        mPaint2.setColor(Color.GREEN);
        mPaint3.setColor(Color.GRAY);
        mPaint4.setColor(Color.GRAY);
        mPaint4.setStrokeWidth(5);
        mPaint4.setTextSize(60);

        mKgWidth = mPaint2.measureText(KG);
        mKgHeight = mPaint2.getFontSpacing();

        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        //获得允许执行一个fling手势动作的最大速度值
        maximumFlingVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        //获得允许执行一个fling手势动作的最小速度值
        minimumFlingVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWeight = "" + Math.round((getWidth() / 2 / UNIT * 0.1 + offset / UNIT) * 10) / 10.0f;
        mNumWidth = mPaint1.measureText(mWeight);
        mNumHeight = mPaint1.getFontSpacing();

        mWeightX = (getWidth() - mNumWidth) / 2;
        mWeightY = getHeight() / 2 - mNumHeight;
        mKgX = getWidth() / 2 + mNumWidth / 2;
        mKgY = getHeight() / 2 - mNumHeight * 2 + mKgHeight;

        mBaseDialY = getHeight()/2;

        canvas.drawText(mWeight, mWeightX, mWeightY, mPaint1);
        canvas.drawText(KG, mKgX, mKgY, mPaint2);

        canvas.drawLine(0, mBaseDialY, getWidth(), mBaseDialY, mPaint3);

        float offsetBy = Math.round(offset / UNIT * 10) / 10.0f; // 刻度
        float oriOffsetBy = offsetBy;

        while ((int)((offsetBy - oriOffsetBy) * 10) * UNIT <= getWidth()) {
            int dialX = Math.round(((offsetBy - oriOffsetBy) * 10 * UNIT));

            if ((int)(offsetBy * 10) % 10 == 0) {
                canvas.drawLine(dialX, mBaseDialY, dialX, mBaseDialY + DIAL_LONG, mPaint4);

                String dial = "" + Math.round(offsetBy);
                float dialWidth = mPaint4.measureText(dial);
                float dialHeight = mPaint4.getFontSpacing();
                canvas.drawText(dial, dialX - dialWidth / 2, mBaseDialY + DIAL_LONG + dialHeight, mPaint4);
            } else {
                canvas.drawLine(dialX, mBaseDialY, dialX, mBaseDialY + DIAL_SHORT, mPaint3);
            }
            offsetBy = Math.round((offsetBy + 0.1) *10) / 10.0f;
        }

        canvas.drawLine(getWidth() / 2, mBaseDialY, getWidth() / 2, mBaseDialY + DIAL_CENTER, mPaint1);

    }

    private void moveBy(float len) {
        offset += len;
        if (offset < 0) {
            offset = 0;
        }
        invalidate();
    }

//    private void startMoveBy(float len) {
//        startAnimation(offset, offset + len);
//    }
//
//    private void startAnimation(float startOffset, float endOffset) {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "offset", startOffset, endOffset);
//        animator.setDuration(3000);
//        animator.start();
//    }

    public void setOffset(float offset) {
        this.offset = offset;
        invalidate();
    }

    public float getOffset() {
        return this.offset;
    }

    private float lastX = 0;
    private float lastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event); // 1
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                float downY = event.getY();
                lastX = downX;
                lastY = downY;

                // 当手指按下的时候，让滑动停止
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                //手指按下的时 重新进行速度追踪
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);

                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                float offsetX = moveX - lastX;
                float offsetY = moveY - lastY;
                if (Math.abs(offsetX) < Math.abs(offsetY)) {
                    return false;
                }
                moveBy(-offsetX / 4);
                lastX = moveX;
                lastY = moveY;
                break;
            case MotionEvent.ACTION_UP:
//                startMoveBy(500);

                // 计算 1000 毫秒内的速度，maximumFlingVelocity 用来限定速度的最大值
                mVelocityTracker.computeCurrentVelocity(1000, maximumFlingVelocity);
                // 获取速度
                int xVelocity = (int) mVelocityTracker.getXVelocity();
                // 大于最小速度才滑动
                if (Math.abs(xVelocity) > minimumFlingVelocity) {
                    // 让 Scroller 开始计算滑动
                    mScroller.fling((int) offset, 0, -xVelocity / 2, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                    invalidate();
                }

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            offset = mScroller.getCurrX(); // 2
            if (offset < 0) {
                offset = 0;
            }
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
