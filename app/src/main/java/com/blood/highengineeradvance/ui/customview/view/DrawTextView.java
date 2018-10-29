package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.util.LogUtil;

public class DrawTextView extends View {
    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(120);

        String text1 = "共产党万岁";
        canvas.drawText(text1, 0, text1.length(), 50, 100, mPaint);

        canvas.translate(50, 150);

        String text2 = "woshishuilibaibaioyideshenziniubileyijinengfangdasixiajiechufengyimozhou";
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(80);
        StaticLayout staticLayout = new StaticLayout(text2, textPaint, 500, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        staticLayout.draw(canvas);

        String text3 = "Hello My World !";
        mPaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(text3, 0, text3.length(), 50, 800, mPaint);

        mPaint.setTypeface(Typeface.SERIF);
//        mPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Satisfy-Regular.ttf"));  // 字体
        canvas.drawText(text3, 0, text3.length(), 50, 1000, mPaint);

        mPaint.setTextSize(50);
        mPaint.setFakeBoldText(false);  // 伪粗体
        canvas.drawText(text3, 0, text3.length(), 50, 1200, mPaint);

        mPaint.setFakeBoldText(true);  // 伪粗体
        canvas.drawText(text3, 0, text3.length(), 50, 1300, mPaint);

        mPaint.setStrikeThruText(true);
        canvas.drawText(text3, 0, text3.length(), 50, 1400, mPaint);

        mPaint.setUnderlineText(true);
        canvas.drawText(text3, 0, text3.length(), 50, 1500, mPaint);

        mPaint.setTextSkewX(-0.5f);
        canvas.drawText(text3, 0, text3.length(), 50, 1600, mPaint);

        mPaint.setTextSkewX(-1.0f); // 倾斜度
        canvas.drawText(text3, 0, text3.length(), 50, 1700, mPaint);

        mPaint.reset();
        mPaint.setTextSize(80);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setTextScaleX(1.5f); // 横向缩放, 粗细
        canvas.drawText(text3, 0, text3.length(), 50, 1800, mPaint);



    }
}
