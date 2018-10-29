package com.blood.highengineeradvance.ui.customview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PieView extends View {

    public PieView(Context context) {
        super(context);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(100, 100, 500, 500, -60, 50, true, mPaint);

        mPaint.setColor(Color.GRAY);
        canvas.drawArc(100, 100, 500, 500, -10, 20, true, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawArc(100, 100, 500, 500, 10, 170, true, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawArc(90, 90, 490, 490, -180, 120, true, mPaint);
    }
}
