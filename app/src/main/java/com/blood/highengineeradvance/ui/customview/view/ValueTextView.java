package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.blood.highengineeradvance.util.LogUtil;

import java.util.Arrays;

public class ValueTextView extends View {
    public ValueTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint1.setTextSize(60);
        paint2.setTextSize(120);
        paint2.setColor(Color.parseColor("#E91E63"));

        measuredText1 = paint1.measureText(text1);
        measuredText2 = paint2.measureText(text2);
    }

    private Paint mPaint = new Paint();

    private String text = "李白 清歌 千年妖狐 H J";

    private Rect rect = new Rect();

    private  float[] widths = new float[text.length()];

    String text1 = "三个月内你胖了";
    String text2 = "4.5";
    String text3 = "公斤";

    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

    float measuredText1;
    float measuredText2;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float fontSpacing = mPaint.getFontSpacing();
        LogUtil.i("fontSpacing - " + fontSpacing);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        LogUtil.i("fontMetrics - " + fontMetrics.ascent + " / "  + fontMetrics.descent
                + " / " + fontMetrics.top + " / " + fontMetrics.bottom
                + " / " + fontMetrics.leading);

        mPaint.setTextSize(100);

        canvas.drawText(text, 50, 100, mPaint);

        mPaint.getTextBounds(text, 0, text.length(), rect);

        LogUtil.i(rect.left + " / " + rect.top + " / " + rect.right + " / " + rect.bottom);

        rect.left += 50;
        rect.top += 100;
        rect.right += 50;
        rect.bottom += 100;

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect, mPaint); // 文字显示范围

        canvas.drawText(text, 50, 250, mPaint);

        float width = mPaint.measureText(text); // 文字的长度, 包含空隙间隔长度
        canvas.drawLine(50, 250, 50 + width, 250, mPaint);

        mPaint.getTextWidths(text, widths);

        for (int i = 0; i < widths.length; i++) {
            LogUtil.i("widths -- " + widths[i]);
        }

        canvas.drawText(text1, 50, 400, paint1);
        canvas.drawText(text2, 50 + measuredText1, 400, paint2);
        canvas.drawText(text3, 50 + measuredText1 + measuredText2, 400, paint1);


    }
}
