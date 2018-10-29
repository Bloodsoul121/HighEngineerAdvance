package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class EffectView extends View {
    public EffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.STROKE);

        PathEffect pathEffect = new DashPathEffect(new float[]{20,10}, 1000); // 第二个参数是偏移, 其实就是左右偏移, 下笔的偏移量
        mPaint.setPathEffect(pathEffect);

        canvas.drawCircle(300, 300, 200, mPaint);
    }
}
