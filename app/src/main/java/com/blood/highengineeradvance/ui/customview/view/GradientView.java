package com.blood.highengineeradvance.ui.customview.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.R;

public class GradientView extends View {
    public GradientView(Context context) {
        super(context);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        @SuppressLint("DrawAllocation") LinearGradient linearGradient = new LinearGradient(0, 0, 400, 400,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawCircle(200, 200, 200, mPaint);

        @SuppressLint("DrawAllocation") RadialGradient radialGradient = new RadialGradient(700, 200, 200,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        mPaint.setShader(radialGradient);
        canvas.drawCircle(700, 200, 200, mPaint);

        @SuppressLint("DrawAllocation") SweepGradient sweepGradient = new SweepGradient(200, 700, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"));
        mPaint.setShader(sweepGradient);
        canvas.drawCircle(200, 700, 200, mPaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
        canvas.drawCircle(700, 700, 200, mPaint);

//        setLayerType(LAYER_TYPE_SOFTWARE, mPaint); // 要关闭硬件加速, 不然, ComposeShader无法生效, 但是也会导致绘制非常卡顿

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);
        BitmapShader bitmapShader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        ComposeShader composeShader = new ComposeShader(bitmapShader, bitmapShader2, PorterDuff.Mode.SRC_OVER);
        mPaint.setShader(composeShader);
        canvas.drawCircle(200, 1200, 200, mPaint);

    }
}
