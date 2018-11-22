package com.blood.highengineeradvance.material_design.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.blood.highengineeradvance.R;

public class BottomBehavior extends CoordinatorLayout.Behavior {

    private int id;
    private float bottomPadding;
    private int screenWidth;
    private float designWidth = 375.0f;//设计视图的宽度，通常是375dp，

    public BottomBehavior() {
        super();
    }

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenWidth = getScreenWidth(context);
        TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.BottomBehavior);
        id = typedArray.getResourceId(R.styleable.BottomBehavior_anchor_id, -1);
        bottomPadding = typedArray.getFloat(R.styleable.BottomBehavior_bottom_padding, 0f);
        typedArray.recycle();
    }

    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params) {
        params.dodgeInsetEdges = Gravity.BOTTOM;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency.getId() == id;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        // xml里图片的宽高比为375:156，其实个人感觉下面的计算没有必要，直接利用top的值来做平移
        child.setTranslationY(-(dependency.getTop() - (screenWidth * bottomPadding / designWidth)));
        Log.e("BottomBehavior", "layoutDependsOn() called with: parent = [" + dependency.getTop());
        return true;
    }

    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display;
        if (wm != null) {
            display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return 0;
    }

}
