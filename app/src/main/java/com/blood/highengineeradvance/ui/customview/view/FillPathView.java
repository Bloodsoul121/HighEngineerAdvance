package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class FillPathView extends View {

    private final Path mPath;
    private final Path mDscPath;

    public FillPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPath = new Path();
        mPath.moveTo(50, 50);
        mPath.rLineTo(100, 20);
        mPath.rLineTo(0, 60);
        mPath.rLineTo(-60, 60);

        mDscPath = new Path();
    }

    private Paint mPaint = new Paint();

    private Paint mPathPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setLayerType(LAYER_TYPE_SOFTWARE, mPaint); // 1

        mPathPaint.setStyle(Paint.Style.STROKE);

        canvas.save();

        canvas.translate(50, 50);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        mPaint.getFillPath(mPath, mDscPath); // 开启软件加速才能有效显示

        canvas.drawPath(mPath, mPaint);

        canvas.restore();

        canvas.save();

        canvas.translate(300, 50);

        canvas.drawPath(mDscPath, mPathPaint);

        canvas.restore();

    }
}
