package com.blood.highengineeradvance.ui.customview.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class CustomWHView extends android.support.v7.widget.AppCompatImageView {

    public CustomWHView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        if (measuredWidth < measuredHeight) {
            measuredHeight = measuredWidth;
        } else if (measuredWidth > measuredHeight) {
            measuredWidth = measuredHeight;
        }

        int width = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.AT_MOST);
        int height = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST);

        setMeasuredDimension(width, height);
    }

}
