package com.blood.highengineeradvance.ui.customview.animate;


import android.animation.TypeEvaluator;

import com.blood.highengineeradvance.util.LogUtil;

public class PointEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int x = (int) (startValue.getX() + fraction * (endValue.getX() - startValue.getX()));
        int y = (int) (startValue.getY() + fraction * (endValue.getY() - startValue.getY()));
        Point point = new Point(x, y);

        LogUtil.i("PointEvaluator fraction - " + fraction);

        return point;
    }
}
