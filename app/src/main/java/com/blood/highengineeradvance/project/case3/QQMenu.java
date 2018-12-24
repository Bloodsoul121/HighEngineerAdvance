package com.blood.highengineeradvance.project.case3;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.blood.highengineeradvance.util.DensityUtils;

public class QQMenu extends HorizontalScrollView {

    private int mScreenWidthPixels;
    private int mMenuPaddingRight;
    private boolean call;
    private int mMenuWith;

    public QQMenu(Context context) {
        super(context);
    }

    public QQMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        mScreenWidthPixels = dm.widthPixels;
        mMenuPaddingRight = (int) DensityUtils.dp2px(context, 50);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!call) {
            LinearLayout container = (LinearLayout) getChildAt(0);
            View menu = container.getChildAt(0);
            View content = container.getChildAt(1);

            mMenuWith = menu.getLayoutParams().width = mScreenWidthPixels - mMenuPaddingRight;
            content.getLayoutParams().width = mScreenWidthPixels;

            call = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWith, 0);
        }
    }
}
