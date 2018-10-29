package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.R;

public class XfermodeView extends View {
    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint paint = new Paint();

    Xfermode xfermode1 = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    Xfermode xfermode2 = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    Xfermode xfermode3 = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(bitmap1, 0, 0, paint);
        paint.setXfermode(xfermode1);
        canvas.drawBitmap(bitmap2, 0, 0, paint);
        paint.setXfermode(null);

        canvas.drawBitmap(bitmap1, bitmap1.getWidth() + 100, 0, paint);
        paint.setXfermode(xfermode2);
        canvas.drawBitmap(bitmap2, bitmap1.getWidth() + 100, 0, paint);
        paint.setXfermode(null);

        canvas.drawBitmap(bitmap1, 0, bitmap1.getHeight() + 20, paint);
        paint.setXfermode(xfermode3);
        canvas.drawBitmap(bitmap2, 0, bitmap1.getHeight() + 20, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(saved);
    }
}
