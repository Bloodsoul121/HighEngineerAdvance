package com.blood.highengineeradvance.statusbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;

import java.lang.reflect.Field;

public class StatusBar {

    public static void setStatusBarColor(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusbarLollipop.setStatusBarColor(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusbarKitKat.setStatusBarColor(activity, statusColor);
        }
    }

}
