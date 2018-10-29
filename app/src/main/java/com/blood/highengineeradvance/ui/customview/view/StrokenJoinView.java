package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class StrokenJoinView extends View {

    private final Path mPath;

    public StrokenJoinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPath = new Path();
        mPath.rLineTo(200, 100);
        mPath.rLineTo(-100, 60);
    }

    private Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.translate(100, 100);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();

    }
}
