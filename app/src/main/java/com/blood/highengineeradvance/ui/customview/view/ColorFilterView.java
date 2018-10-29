package com.blood.highengineeradvance.ui.customview.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.util.LogUtil;


public class ColorFilterView extends View {
    public ColorFilterView(Context context) {
        super(context);
        LogUtil.i("ColorFilterView(Context context)");
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LogUtil.i("ColorFilterView(Context context, @Nullable AttributeSet attrs)");
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtil.i("ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)");
    }

    private Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LightingColorFilter lightingColorFilter = new LightingColorFilter(0x00ffff, 0x000000);
        mPaint.setColorFilter(lightingColorFilter);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.what_the_fuck);
        canvas.drawBitmap(bitmap3, 0, 0, mPaint);

        @SuppressLint("DrawAllocation") ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0); // 饱和度
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        mPaint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(bitmap3, 0, 1200, mPaint);
    }
}
