package com.blood.highengineeradvance.ui.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MatrixView extends View {
    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Matrix mMatrix = new Matrix();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mMatrix.reset();
//        mMatrix.postTranslate();
//        mMatrix.postRotate();
    }
}
