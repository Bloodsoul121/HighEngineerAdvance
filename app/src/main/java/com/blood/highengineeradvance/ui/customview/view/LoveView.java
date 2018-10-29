package com.blood.highengineeradvance.ui.customview.view;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LoveView extends View {
    public LoveView(Context context) {
        super(context);
    }

    public LoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.addArc(200, 200, 400, 400, -225, 225);
        mPath.arcTo(400, 200, 600, 400, -180, 225, false);
        mPath.lineTo(400, 542);

        canvas.drawPath(mPath, mPaint);
    }
}
