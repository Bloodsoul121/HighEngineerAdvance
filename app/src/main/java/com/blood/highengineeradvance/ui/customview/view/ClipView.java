package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.R;

public class ClipView extends View {

    private final Bitmap mBitmap1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPath1.addArc(0, 0, 800, 800, 0, 360);

        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.batman);

        mPaint1.setStyle(Paint.Style.FILL);
    }

    private Path mPath1 = new Path();

    private Paint mPaint1 = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.clipPath(mPath1);
        canvas.drawBitmap(mBitmap1, 0, 0, mPaint1);
        canvas.restore();


    }
}
