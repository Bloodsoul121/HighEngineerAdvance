package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TextPathView extends View {
    public TextPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint();

    private Paint mPathPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setLayerType(LAYER_TYPE_SOFTWARE, mPathPaint); // 1  软件加速

        mPaint.setTextSize(120);

        String text = "来咬我啊";
        canvas.drawText(text, 0, text.length(), 50, 150, mPaint);

        Path path = new Path();
        mPaint.getTextPath(text, 0, text.length(), 50, 150, path);

        canvas.translate(200, 50);

        mPathPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPathPaint);

    }
}
