package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class StrokenCapView extends View {
    public StrokenCapView(Context context) {
        super(context);
    }

    public StrokenCapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeWidth(40);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100,100,300,100,mPaint);

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100,300,300,300,mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(100,500,300,500,mPaint);

    }
}
