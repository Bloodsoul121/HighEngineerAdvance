package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomTextView extends View {
    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(100);

        mPaint.setShadowLayer(20, 0, 0, Color.RED);

        canvas.drawText("你是个傻逼", 50, 200, mPaint);
    }
}
