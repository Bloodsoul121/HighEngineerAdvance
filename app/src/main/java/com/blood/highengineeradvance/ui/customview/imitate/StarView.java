package com.blood.highengineeradvance.ui.customview.imitate;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.R;

public class StarView extends View implements View.OnClickListener {

    private final Bitmap mStarBitmap;
    private final Bitmap mUnstarBitmap;

    public StarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOnClickListener(this);

        mPaint.setTextSize(100);

        mStarBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round);
        mUnstarBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }

    private Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int currentNum = 1399;
    private int originNum;
    private int mZeroCountDiff;
    private boolean isStar;
    private boolean isInit = true;
    private float startX = 50;
    private float startY = 200;
    private float mProgress;

    private static final float oriBitmapX = 50;
    private static final float oriBitmapY = 100;

    private static final float IncreaseBitmapScale = 0.2f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String numText = String.valueOf(currentNum);
        float fontSpacing = mPaint.getFontSpacing();

        canvas.save();

        int mStarBitmapWidth = mStarBitmap.getWidth();
        int mStarBitmapHeight = mStarBitmap.getHeight();
        int mUnstarBitmapWidth = mStarBitmap.getWidth();
        int mUnstarBitmapHeight = mStarBitmap.getHeight();

        if (isStar) {
            float scale = 1f + IncreaseBitmapScale * mProgress;
            canvas.scale(scale, scale, oriBitmapX + mStarBitmapWidth / 2, oriBitmapY + mStarBitmapHeight / 2);
            canvas.drawBitmap(mStarBitmap, oriBitmapX, oriBitmapY, mBitmapPaint);
            startX = oriBitmapX + mStarBitmapWidth;
        } else {
            canvas.drawBitmap(mUnstarBitmap, oriBitmapX, oriBitmapY, mBitmapPaint);
            startX = oriBitmapX + mUnstarBitmapWidth;
        }

        canvas.restore();

        if (isInit) {
            canvas.drawText(numText, startX, startY, mPaint);
            return;
        }

        int offset = Math.abs(mZeroCountDiff) + 1;

        String firstText = numText.substring(0, numText.length() - offset);
        String secondText = numText.substring(numText.length() - offset);
        String oriSecondText = "" + (int)(originNum % Math.pow(10, offset));

        canvas.drawText(firstText, startX, startY, mPaint);

        float firstTextLength = mPaint.measureText(firstText);
        float TextLength = mPaint.measureText(numText);

        canvas.save();

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        canvas.clipRect(startX, startY + top, startX + TextLength, startY + bottom);

        if (Integer.valueOf(secondText) > Integer.valueOf(oriSecondText)) {
            canvas.drawText(oriSecondText, startX + firstTextLength, startY - fontSpacing * mProgress, mPaint);
            canvas.drawText(secondText, startX + firstTextLength, startY + fontSpacing * (1 - mProgress), mPaint);
        } else {
            canvas.drawText(oriSecondText, startX + firstTextLength, startY + fontSpacing * mProgress, mPaint);
            canvas.drawText(secondText, startX + firstTextLength, startY - fontSpacing * (1 - mProgress), mPaint);
        }

        canvas.restore();

    }

    public void setCurrentNum(int value) {
        currentNum = value;
        isInit = true;
        invalidate();
    }

    @Override
    public void onClick(View v) {
        isStar = !isStar;
        isInit = false;
        originNum = currentNum;

        if (isStar) {
            currentNum += 1;
        } else {
            currentNum -= 1;
        }

        int oriZeroCount = getZeroCount(originNum);
        int curZeroCount = getZeroCount(currentNum);

        mZeroCountDiff = curZeroCount - oriZeroCount;

        startAnimation();

        invalidate();
    }

    private int getZeroCount(int num) {
        int count = 0;
        String s = String.valueOf(num);
        int index = 0;
        while ((index = s.indexOf('0', index)) != -1) {
            count++;
            index++;
        }
        return count;
    }

    private void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 1);
        animator.setDuration(300);
        animator.start();
    }

    private void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    private float getProgress() {
        return mProgress;
    }

}
