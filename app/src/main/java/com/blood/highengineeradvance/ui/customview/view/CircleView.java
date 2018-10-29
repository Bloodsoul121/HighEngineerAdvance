package com.blood.highengineeradvance.ui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.util.LogUtil;

public class CircleView extends View {

    private Paint mPaint = new Paint();

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);

        LogUtil.i("CircleView " + mPaint.getStrokeWidth());

        canvas.drawCircle(200, 200, 150, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(500, 200, 100, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

        canvas.drawCircle(200, 500, 100, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(500, 500, 100, mPaint);

        mPaint.reset();

    }
}
